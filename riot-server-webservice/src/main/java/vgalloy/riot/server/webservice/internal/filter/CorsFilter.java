package vgalloy.riot.server.webservice.internal.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * Created by Vincent Galloy on 20/06/16.
 *
 * @author Vincent Galloy
 */
@Component
public class CorsFilter implements SimpleFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        setHeader(res, "Access-Control-Allow-Origin", req.getHeader("Origin"));
        setHeader(res, "Access-Control-Allow-Credentials", "true");
        setHeader(res, "Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        setHeader(res, "Access-Control-Max-Age", "3600");
        setHeader(res, "Access-Control-Allow-Headers", "Content-Type, *");

        chain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Set the header into the request.
     *
     * @param httpServletResponse the httpServletResponse
     * @param name                the name of the header
     * @param value               the value of the header (can be null)
     * @throws UnsupportedEncodingException if the UTF_8 Charsets is not supported
     */
    private void setHeader(HttpServletResponse httpServletResponse, String name, String value) throws UnsupportedEncodingException {
        String newValue = Optional.ofNullable(value).orElseGet(() -> "");
        String encodedValue = URLEncoder.encode(newValue, StandardCharsets.UTF_8.displayName());
        httpServletResponse.setHeader(name, encodedValue);
    }
}
