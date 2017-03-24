package vgalloy.riot.server.webservice.api.conf;

import org.junit.Test;
import org.springframework.boot.SpringApplication;

import vgalloy.riot.server.webservice.api.WebserviceConfig;

/**
 * Created by Vincent Galloy on 23/08/16.
 *
 * @author Vincent Galloy
 */
public final class WebserviceConfigTest {

    @Test
    public void testStartApplication() {
        SpringApplication.run(WebserviceConfig.class);
    }
}