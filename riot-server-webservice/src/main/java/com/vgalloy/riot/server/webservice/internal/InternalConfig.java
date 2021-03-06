package com.vgalloy.riot.server.webservice.internal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.vgalloy.riot.server.service.api.factory.ServiceFactory;
import com.vgalloy.riot.server.service.api.service.ChampionService;
import com.vgalloy.riot.server.service.api.service.ItemService;
import com.vgalloy.riot.server.service.api.service.MatchDetailService;
import com.vgalloy.riot.server.service.api.service.RankedStatsService;
import com.vgalloy.riot.server.service.api.service.SummonerService;
import com.vgalloy.riot.server.webservice.internal.conf.JacksonConfiguration;
import com.vgalloy.riot.server.webservice.internal.conf.SwaggerConfig;

/**
 * Created by Vincent Galloy on 24/08/16.
 *
 * @author Vincent Galloy
 */
@Import({JacksonConfiguration.class, SwaggerConfig.class})
@ComponentScan
@Configuration
public class InternalConfig {

    /**
     * Create ItemService bean.
     *
     * @return the bean
     */
    @Bean
    public ChampionService championService() {
        return ServiceFactory.getChampionService();
    }

    /**
     * Create ItemService bean.
     *
     * @return the bean
     */
    @Bean
    public ItemService itemService() {
        return ServiceFactory.getItemService();
    }

    /**
     * Create MatchDetailService bean.
     *
     * @return the bean
     */
    @Bean
    public MatchDetailService matchDetailService() {
        return ServiceFactory.getMatchDetailService();
    }

    /**
     * Create MatchDetailService bean.
     *
     * @return the bean
     */
    @Bean
    public RankedStatsService rankedStatsService() {
        return ServiceFactory.getRankedStatsService();
    }

    /**
     * Create SummonerService bean.
     *
     * @return the bean
     */
    @Bean
    public SummonerService summonerService() {
        return ServiceFactory.getSummonerService();
    }
}
