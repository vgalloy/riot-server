package vgalloy.riot.server.webservice.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 18/06/16.
 */
@RestController
@PropertySource(value = "classpath:META-INF/maven/vgalloy/riot-server-webservice/pom.properties", ignoreResourceNotFound = true)
public class HomeController {

    @Value("${version:none}")
    private String version;

    /**
     * Get the home page.
     *
     * @return Some information
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHome() {
        return "{\"status\": \"running\", \"version\":\"" + version + "\"}";
    }
}
