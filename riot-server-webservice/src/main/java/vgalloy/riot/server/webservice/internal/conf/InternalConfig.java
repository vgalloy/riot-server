package vgalloy.riot.server.webservice.internal.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import vgalloy.riot.server.service.api.factory.ServiceFactory;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 24/08/16.
 */
@Import(SwaggerConfig.class)
@ComponentScan(value = {
        "vgalloy.riot.server.webservice.internal.filter",
        "vgalloy.riot.server.webservice.internal.controller",
        "vgalloy.riot.server.webservice.api.controller"})
@Configuration
public class InternalConfig {

    /**
     * Create QueryService bean.
     *
     * @return the bean
     */
    @Bean
    public QueryService queryService() {
        return ServiceFactory.getQueryService();
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
