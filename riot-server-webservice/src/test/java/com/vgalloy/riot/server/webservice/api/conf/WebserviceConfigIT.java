package com.vgalloy.riot.server.webservice.api.conf;

import org.junit.Test;
import org.springframework.boot.SpringApplication;

import com.vgalloy.riot.server.webservice.api.WebserviceConfig;

/**
 * Created by Vincent Galloy on 23/08/16.
 *
 * @author Vincent Galloy
 */
public final class WebserviceConfigIT {

    @Test
    public void testStartApplication() {
        SpringApplication.run(WebserviceConfig.class);
    }
}