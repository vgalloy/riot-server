package vgalloy.riot.server.webservice.api.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;
import vgalloy.riot.server.webservice.internal.conf.InternalConfig;

/**
 * @author Created by Vincent Galloy on 23/08/16.
 */
@SpringBootApplication(scanBasePackages = "vgalloy.riot.server.webservice.api.controller", exclude = ErrorMvcAutoConfiguration.class)
@Import(InternalConfig.class)
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
