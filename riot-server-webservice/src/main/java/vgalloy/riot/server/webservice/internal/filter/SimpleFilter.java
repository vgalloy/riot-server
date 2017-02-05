package vgalloy.riot.server.webservice.internal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 05/02/17.
 */
public interface SimpleFilter extends Filter {

    @Override
    default void init(FilterConfig filterConfig) throws ServletException {
        // Tumbleweed
    }

    @Override
    default void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Tumbleweed
    }

    @Override
    default void destroy() {
        // Tumbleweed
    }
}
