package vgalloy.riot.server.webservice.api.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Import;

import vgalloy.riot.server.webservice.internal.conf.InternalConfig;

/**
 * Created by Vincent Galloy on 23/08/16.
 *
 * @author Vincent Galloy
 */
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class, MongoAutoConfiguration.class})
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
