package vgalloy.riot.server.service.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import vgalloy.riot.server.service.internal.config.DatabaseDaoConfig;
import vgalloy.riot.server.service.internal.config.InternalServiceConfig;
import vgalloy.riot.server.service.internal.config.LoaderConfig;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Configuration
@Import({DatabaseDaoConfig.class, InternalServiceConfig.class, LoaderConfig.class})
public class ServiceConfig {

}
