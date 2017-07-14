package com.vgalloy.riot.server.webservice.internal.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vgalloy.riot.server.webservice.api.controller.HomeController;
import com.vgalloy.riot.server.webservice.api.dto.impl.VersionDto;

/**
 * Created by Vincent Galloy on 18/06/16.
 *
 * @author Vincent Galloy
 */
@RestController
@PropertySource(value = "classpath:META-INF/maven/vgalloy/riot-server-webservice/pom.properties", ignoreResourceNotFound = true)
final class HomeControllerImpl implements HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeControllerImpl.class);

    @Value("${version:none}")
    private String version;

    @Override
    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    public VersionDto getHome() {
        LOGGER.info("[ GET ] : getHome");
        VersionDto versionDto = new VersionDto();
        versionDto.setStatus("running");
        versionDto.setVersion(version);
        return versionDto;
    }
}
