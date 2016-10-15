package vgalloy.riot.server.webservice.internal.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author Vincent Galloy - 19/09/16
 *         Created by Vincent Galloy on 19/09/16.
 */
@Component
public class IpLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestUuid = UUID.randomUUID().toString();
        MDC.put("requestUuid", requestUuid);

        LOGGER.info("ip : {}", servletRequest.getRemoteAddr());

        long startTimeNano = System.nanoTime();
        filterChain.doFilter(servletRequest, servletResponse);
        LOGGER.info("execution time : {}", System.nanoTime() - startTimeNano);
    }

    @Override
    public void destroy() {

    }
}
