package vgalloy.riot.server.webservice.api.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Value("${version:none}")
    private String version;

    /**
     * Get the home page.
     *
     * @return Some information
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Version getHome() {
        LOGGER.info("[ GET ] : getHome");
        return new Version("running", version);
    }

    private class Version {

        private final String status;
        private final String version;

        /**
         * Constructor.
         *
         * @param status  the webApp status
         * @param version the version as a string
         */
        Version(String status, String version) {
            this.status = Objects.requireNonNull(status);
            this.version = Objects.requireNonNull(version);
        }

        public String getStatus() {
            return status;
        }

        public String getVersion() {
            return version;
        }
    }
}
