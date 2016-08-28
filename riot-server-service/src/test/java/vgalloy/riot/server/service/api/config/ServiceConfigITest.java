package vgalloy.riot.server.service.api.config;

import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import vgalloy.riot.api.api.model.RiotApiKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public class ServiceConfigITest {

    @Test
    public void testStartContext() {
        try {
            ConfigurableApplicationContext configurableApplicationContext = new SpringApplicationBuilder().sources(ServiceConfig.class).run();
            RiotApiKey riotApiKey = configurableApplicationContext.getBean(RiotApiKey.class);
            assertEquals("myApiKey", riotApiKey.getApiKey());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
