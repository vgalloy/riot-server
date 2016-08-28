package vgalloy.riot.server.webservice.api.conf;

import org.junit.Test;
import org.springframework.boot.SpringApplication;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public class WebserviceConfigITest {

    @Test
    public void testStartApplication() {
        SpringApplication.run(WebserviceConfig.class);
    }
}