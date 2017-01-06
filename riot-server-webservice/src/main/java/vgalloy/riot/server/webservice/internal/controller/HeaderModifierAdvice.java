package vgalloy.riot.server.webservice.internal.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Vincent Galloy - 27/12/16
 *         Created by Vincent Galloy on 27/12/16.
 *         This class must add X_EXECUTION_TIME_MILLIS header for each request
 */
@ControllerAdvice
public class HeaderModifierAdvice implements ResponseBodyAdvice<Object>, Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderModifierAdvice.class);
    private static final String X_EXECUTION_TIME_MILLIS = "X-execution-time-millis";

    private final ThreadLocal<Long> requestStartTimeMillis = new ThreadLocal<>();

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Long executionTimeMillis = null;

        if (requestStartTimeMillis.get() != null) {
            executionTimeMillis = System.currentTimeMillis() - requestStartTimeMillis.get();
        }

        LOGGER.info("execution time : {} ms", executionTimeMillis);
        serverHttpResponse.getHeaders().set(HeaderModifierAdvice.X_EXECUTION_TIME_MILLIS, String.valueOf(executionTimeMillis));

        return body;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        requestStartTimeMillis.set(System.currentTimeMillis());
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
