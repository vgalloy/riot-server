package vgalloy.riot.server.service.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vgalloy.riot.api.rest.request.game.dto.RecentGamesDto;
import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchReference;
import vgalloy.riot.api.rest.request.stats.dto.RankedStatsDto;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.service.internal.config.InternalServiceConfig;
import vgalloy.riot.server.service.internal.config.LoaderConfig;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Configuration
@Import({InternalServiceConfig.class, LoaderConfig.class})
public class ServiceTestConfig {

    /**
     * Get MatchDetailDao.
     *
     * @return the match detail dao
     */
    @Bean
    public CommonDao<MatchDetail> getMatchDetailDao() {
        return null;
    }

    /**
     * Get MatchReferenceDao.
     *
     * @return the match reference dao
     */
    @Bean
    public CommonDao<MatchReference> getMatchReferenceDao() {
        return null;
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public CommonDao<RankedStatsDto> getRankedStatsDao() {
        return null;
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public CommonDao<RecentGamesDto> getRecentGamesDao() {
        return null;
    }

    /**
     * Get SummonerDao.
     *
     * @return the summoner dao
     */
    @Bean
    public CommonDao<SummonerDto> getSummonerDao() {
        return null;
    }

    /**
     * Get SummonerDao.
     *
     * @return the summoner dao
     */
    @Bean
    public QueryDao queryDao() {
        return null;
    }
}
