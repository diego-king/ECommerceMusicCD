package ca.edu.uottawa.csi5380.controller;

import ca.edu.uottawa.csi5380.exception.response.ExceptionJsonInfo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Global base exception handler for REST services.
 *
 * @author Kenny Byrd
 */
public abstract class BaseExceptionHandler {

    protected final Logger LOGGER = LogManager.getLogger(BaseExceptionHandler.class);
    private final Map<Class, HttpStatus> exceptionMappings = new HashMap<>();

    /**
     * Base method to swallow and log all exceptions occurring in the server when a REST
     * method is invoked.
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ExceptionJsonInfo handleThrowable(final Throwable ex, final HttpServletRequest request, final HttpServletResponse response) {
        HttpStatus status = exceptionMappings.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        response.setStatus(status.value());
        LOGGER.error(ex.getMessage(), ex);
        return new ExceptionJsonInfo(request.getRequestURL().toString(),
                status.equals(HttpStatus.INTERNAL_SERVER_ERROR) ? "Internal server error occurred." : ex.getMessage());
    }

    protected void registerMapping(final Class<?> clazz, final HttpStatus status) {
        exceptionMappings.put(clazz, status);
    }

}
