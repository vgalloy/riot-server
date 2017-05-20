package vgalloy.riot.server.webservice.internal.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * Created by Vincent Galloy on 19/09/16.
 *
 * @author Vincent Galloy
 */
@Component
final class CorrelationIdFilter implements SimpleFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationIdFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestUuid = UUID.randomUUID().toString();
        MDC.put("requestUuid", requestUuid);
        LOGGER.info("add correlation Id : {}", requestUuid);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
