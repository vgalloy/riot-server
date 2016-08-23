package vgalloy.riot.server.service.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import vgalloy.riot.api.client.ratelimite.model.RateLimit;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.service.RiotApi;
import vgalloy.riot.api.service.RiotApiKey;
import vgalloy.riot.server.service.internal.loader.Loader;
import vgalloy.riot.server.service.internal.loader.impl.matchdetail.MatchDetailLoader;
import vgalloy.riot.server.service.internal.loader.impl.matchreference.MatchReferenceLoader;
import vgalloy.riot.server.service.internal.loader.impl.rankedstats.RankedStatsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Configuration
@ComponentScan(value = {"vgalloy.riot.server.service.internal.executor", "vgalloy.riot.server.service.internal.loader"})
public class LoaderConfig {

    @Value("${database.url}")
    private String databaseDaoUrl;
    @Value("${api_key}")
    private String apiKey;

    /**
     * Create the riot api without RiotApiKey.
     *
     * @return the riot api without key
     */
    @Bean
    public RiotApi riotApi() {
        return new RiotApi().addGlobalRateLimit(new RateLimit(5, 10), new RateLimit(400, 10 * 60 * 1000));
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

    /**
     * Create all the loader.
     *
     * @return the list of loader
     */
    @Bean
    public List<Loader> configure() {
        List<Loader> loaderList = new ArrayList<>();
        for (Region region : Region.values()) {
            loaderList.add(new MatchDetailLoader(region));
            loaderList.add(new MatchReferenceLoader(region));
            loaderList.add(new RankedStatsLoader(region));
        }
        return loaderList;
    }
}
