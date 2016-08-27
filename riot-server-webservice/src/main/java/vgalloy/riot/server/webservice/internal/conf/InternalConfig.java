package vgalloy.riot.server.webservice.internal.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vgalloy.riot.server.service.api.config.ServiceConfig;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 24/08/16.
 */
@Import({ServiceConfig.class, SwaggerConfig.class})
@ComponentScan(value = {"vgalloy.riot.server.webservice.internal.filter", "vgalloy.riot.server.webservice.api.controller"})
@Configuration
public class InternalConfig {

}
