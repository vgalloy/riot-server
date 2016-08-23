package vgalloy.riot.server.webservice.api.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vgalloy.riot.server.service.api.config.ServiceConfig;
import vgalloy.riot.server.webservice.internal.conf.SwaggerConfig;

/**
 * @author Created by Vincent Galloy on 23/08/16.
 */
@Configuration
@ComponentScan("vgalloy.riot.server.webservice.api.controller")
@Import({ServiceConfig.class, SwaggerConfig.class})
public class WebserviceConfig {

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    public static void main(String... args) {
        SpringApplication.run(WebserviceConfig.class, args);
    }
}
