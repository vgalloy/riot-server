package vgalloy.riot.server.webservice.internal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import vgalloy.riot.server.service.api.service.exception.UserException;
import vgalloy.riot.server.webservice.internal.model.Error;
import vgalloy.riot.server.webservice.internal.model.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.model.ResourceNotLoadedException;

/**
 * Created by Vincent Galloy on 24/08/16.
 *
 * @author Vincent Galloy
 */
@ControllerAdvice
public final class GlobalErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity handle(Throwable e) {
        LOGGER.error("", e);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ups ... unexpected error occurred ! !");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handle(MethodArgumentTypeMismatchException e) {
        LOGGER.error("{}", e.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Value of '" + e.getName() + "' can not be convert into [" + e.getRequiredType().getSimpleName() + "]");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handle(MissingServletRequestParameterException e) {
        LOGGER.error("{}", e.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "The parameter : " + e.getParameterName() + " is mandatory.");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity handle(UserException e) {
        LOGGER.error("{}", e.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(ResourceDoesNotExistException.class)
    public ResponseEntity handle(ResourceDoesNotExistException e) {
        return buildResponse(HttpStatus.NOT_FOUND, ResourceDoesNotExistException.MESSAGE);
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(ResourceNotLoadedException.class)
    public ResponseEntity handle(ResourceNotLoadedException e) {
        return buildResponse(HttpStatus.ACCEPTED, ResourceNotLoadedException.MESSAGE);
    }

    /**
     * Build a response entity.
     *
     * @param httpStatus the http status
     * @param message    the message
     * @return the response entity
     */
    private ResponseEntity<Error> buildResponse(HttpStatus httpStatus, String message) {
        HttpHeaders headers = new HttpHeaders();
        Error error = new Error(httpStatus.value(), message);
        return new ResponseEntity<>(error, headers, httpStatus);
    }
}
