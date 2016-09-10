package vgalloy.riot.server.service.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import vgalloy.riot.api.api.factory.RiotApiFactory;
import vgalloy.riot.api.api.model.RateLimit;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.model.RiotApiKey;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Configuration
@EnableScheduling
@ComponentScan(value = {"vgalloy.riot.server.service.internal.executor", "vgalloy.riot.server.service.internal.loader"})
public class LoaderConfig {

    @Value("${api_key}")
    private String apiKey;

    /**
     * Create the riot api without RiotApiKey.
     *
     * @return the riot api without key
     */
    @Bean
    public RiotApi riotApi() {
        return RiotApiFactory.newRiotApi().addGlobalRateLimit(new RateLimit(5, 10 * 1000), new RateLimit(400, 10 * 60 * 1000));
    }

    /**
     * Create the riot api key.
     *
     * @return the riot api key
     */
    @Bean
    public RiotApiKey riotApiKey() {
        return new RiotApiKey(apiKey);
    }
}
