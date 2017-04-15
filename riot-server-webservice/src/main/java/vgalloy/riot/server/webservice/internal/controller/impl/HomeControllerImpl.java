package vgalloy.riot.server.webservice.internal.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.server.webservice.api.controller.HomeController;
import vgalloy.riot.server.webservice.api.dto.VersionDto;

/**
 * Created by Vincent Galloy on 18/06/16.
 *
 * @author Vincent Galloy
 */
@RestController
@PropertySource(value = "classpath:META-INF/maven/vgalloy/riot-server-webservice/pom.properties", ignoreResourceNotFound = true)
public class HomeControllerImpl implements HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeControllerImpl.class);

    @Value("${version:none}")
    private String version;

    @Override
    @RequestMapping(value = "/home", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public VersionDto getHome() {
        LOGGER.info("[ GET ] : getHome");
        return new VersionDto("running", version);
    }
}
