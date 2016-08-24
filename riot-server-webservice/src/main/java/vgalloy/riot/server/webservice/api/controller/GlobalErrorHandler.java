package vgalloy.riot.server.webservice.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 24/08/16.
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
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable e) {
        LOGGER.error("{}", e.toString(), e);
        return "Ups ... unexpected error occurred ! !";
    }
}
