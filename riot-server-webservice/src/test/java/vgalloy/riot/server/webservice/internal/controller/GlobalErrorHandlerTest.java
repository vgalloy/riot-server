package vgalloy.riot.server.webservice.internal.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import vgalloy.riot.server.service.api.service.exception.UserException;
import vgalloy.riot.server.webservice.internal.dto.ErrorDto;

/**
 * Created by Vincent Galloy on 10/06/17.
 *
 * @author Vincent Galloy
 */
public final class GlobalErrorHandlerTest {

    private GlobalErrorHandler globalErrorHandler = new GlobalErrorHandler();

    @Test
    public void correctHandlingOfUserException() {
        // GIVEN
        String message = "Custom message";
        UserException input = new UserException(message);

        // WHEN
        ResponseEntity<ErrorDto> result = globalErrorHandler.handle(input);

        // THEN
        Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getBody().getCode());
        Assert.assertEquals(message, result.getBody().getMessage());
    }

    @Test
    public void correctHandlingOfThrowable() {
        // GIVEN
        Throwable input = new Throwable();

        // WHEN
        ResponseEntity<ErrorDto> result = globalErrorHandler.handle(input);

        // THEN
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getBody().getCode());
    }
}