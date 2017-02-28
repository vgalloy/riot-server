package vgalloy.riot.server.webservice.internal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@ResponseBody
@ControllerAdvice
public final class GlobalErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Error handle(Throwable e) {
        LOGGER.error("", e);
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ups ... unexpected error occurred ! !");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Error handle(MethodArgumentTypeMismatchException e) {
        LOGGER.error("{}", e.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), "Value of '" + e.getName() + "' can not be convert into [" + e.getRequiredType().getSimpleName() + "]");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Error handle(MissingServletRequestParameterException e) {
        LOGGER.error("{}", e.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), "The parameter : " + e.getParameterName() + " is mandatory.");
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public Error handle(UserException e) {
        LOGGER.error("{}", e.getMessage());
        return new Error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceDoesNotExistException.class)
    public Error handle(ResourceDoesNotExistException e) {
        return new Error(HttpStatus.NOT_FOUND.value(), ResourceDoesNotExistException.MESSAGE);
    }

    /**
     * Handle error and set the correct response status.
     *
     * @param e The handle exception
     * @return The error message for web user
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(ResourceNotLoadedException.class)
    public Error handle(ResourceNotLoadedException e) {
        return new Error(HttpStatus.ACCEPTED.value(), ResourceNotLoadedException.MESSAGE);
    }
}
