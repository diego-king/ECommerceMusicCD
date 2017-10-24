package ca.edu.uottawa.csi5380.controller;

import ca.edu.uottawa.csi5380.exception.RestException;
import ca.edu.uottawa.csi5380.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlers extends BaseExceptionHandler {

    public ExceptionHandlers() {
        registerMapping(UserNotFoundException.class, HttpStatus.NOT_FOUND);
        registerMapping(RestException.class, HttpStatus.BAD_REQUEST);
    }

}
