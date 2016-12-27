package vgalloy.riot.server.webservice.internal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import vgalloy.riot.server.webservice.internal.filter.TimeExecutionFilter;

/**
 * @author Vincent Galloy - 27/12/16
 *         Created by Vincent Galloy on 27/12/16.
 */
@ControllerAdvice
public class HeaderModifierAdvice implements ResponseBodyAdvice<Object> {

    private static final String X_EXECUTION_TIME_MILLIS = "X-executionTimeMillis";
    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderModifierAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        long startTimeMillis = Long.valueOf(serverHttpResponse.getHeaders().get(TimeExecutionFilter.X_START_TIME_MILLIS).get(0));

        long executionTimeMillis = System.currentTimeMillis() - startTimeMillis;

        LOGGER.info("execution time : {} ms", executionTimeMillis);
        serverHttpResponse.getHeaders().set(HeaderModifierAdvice.X_EXECUTION_TIME_MILLIS, String.valueOf(executionTimeMillis));

        return body;
    }
}
