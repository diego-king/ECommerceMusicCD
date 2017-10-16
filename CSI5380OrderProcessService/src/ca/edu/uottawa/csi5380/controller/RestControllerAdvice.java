package ca.edu.uottawa.csi5380.controller;

import ca.edu.uottawa.csi5380.exception.RestException;
import ca.edu.uottawa.csi5380.exception.response.ExceptionJsonInfo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RestControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(RestControllerAdvice.class);

    /**
     * Swallows and logs RestException. Then returns a JSON object with exception info to the client.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RestException.class)
    public @ResponseBody
    ExceptionJsonInfo handleRestException(final HttpServletRequest request, final RestException e) {
        LOGGER.error(e.getMessage(), e);
        return new ExceptionJsonInfo(request.getRequestURL().toString(), e.getMessage());
    }

    /**
     * Swallows and logs SQLException. Then returns a JSON object with exception info to the client.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public @ResponseBody
    ExceptionJsonInfo handleSQLtException(final HttpServletRequest request, final SQLException e) {
        LOGGER.error(e.getMessage(), e);
        return new ExceptionJsonInfo(request.getRequestURL().toString(), "Internal database error occurred.");
    }

}
