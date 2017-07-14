package com.vgalloy.riot.server.webservice.internal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.vgalloy.riot.server.service.api.service.exception.UserException;
import com.vgalloy.riot.server.webservice.internal.dto.ErrorDto;
import com.vgalloy.riot.server.webservice.internal.exception.ResourceDoesNotExistException;
import com.vgalloy.riot.server.webservice.internal.exception.ResourceNotLoadedException;

/**
 * Created by Vincent Galloy on 24/08/16.
 *
 * @author Vincent Galloy
 */
@ControllerAdvice
final class GlobalErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDto> handle(Throwable e) {
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
    public ResponseEntity<ErrorDto> handle(MethodArgumentTypeMismatchException e) {
        LOGGER.warn("{}", e.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Value of '" + e.getName() + "' can not be convert into [" + e.getRequiredType().getSimpleName() + "]");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handle(HttpMessageNotReadableException e) {
        LOGGER.warn("{}", e.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid json");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDto> handle(MissingServletRequestParameterException e) {
        LOGGER.warn("{}", e.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "The parameter : " + e.getParameterName() + " is mandatory.");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDto> handle(UserException e) {
        LOGGER.warn("{}", e.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(ResourceDoesNotExistException.class)
    public ResponseEntity<ErrorDto> handle(ResourceDoesNotExistException e) {
        return buildResponse(HttpStatus.NOT_FOUND, ResourceDoesNotExistException.MESSAGE);
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ExceptionHandler(ResourceNotLoadedException.class)
    public ResponseEntity<ErrorDto> handle(ResourceNotLoadedException e) {
        return buildResponse(HttpStatus.ACCEPTED, ResourceNotLoadedException.MESSAGE);
    }

    /**
     * Build a response entity.
     *
     * @param httpStatus the http status
     * @param message    the message
     * @return the response entity
     */
    private ResponseEntity<ErrorDto> buildResponse(HttpStatus httpStatus, String message) {
        HttpHeaders headers = new HttpHeaders();
        ErrorDto errorDto = new ErrorDto(httpStatus.value(), message);
        return new ResponseEntity<>(errorDto, headers, httpStatus);
    }
}
