package vgalloy.riot.server.webservice.internal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Vincent Galloy - 19/09/16
 *         Created by Vincent Galloy on 19/09/16.
 */
@Order(2)
@Component
public class TimeExecutionFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeExecutionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTimeNano = System.nanoTime();
        filterChain.doFilter(servletRequest, servletResponse);
        long executionTimeMillis = (System.nanoTime() - startTimeNano) / 1_000_000;

        LOGGER.info("execution time : {} ms", executionTimeMillis);
        ((HttpServletResponse) servletResponse).setHeader("X-executionTimeMillis", executionTimeMillis + " ms");
    }

    @Override
    public void destroy() {

    }
}
