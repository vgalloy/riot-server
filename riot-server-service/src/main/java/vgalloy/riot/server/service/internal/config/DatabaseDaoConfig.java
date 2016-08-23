package vgalloy.riot.server.service.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vgalloy.riot.api.rest.request.game.dto.RecentGamesDto;
import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchReference;
import vgalloy.riot.api.rest.request.stats.dto.RankedStatsDto;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.provider.MongoDaoProvider;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 15/07/16.
 */
@Configuration
public class DatabaseDaoConfig {

    @Value("${database.url}")
    private String databaseDaoUrl;

    /**
     * Get MatchDetailDao.
     *
     * @return the match detail dao
     */
    @Bean
    public CommonDao<MatchDetail> matchDetailDao() {
        return MongoDaoProvider.getMatchDetailDao(databaseDaoUrl);
    }

    /**
     * Get MatchReferenceDao.
     *
     * @return the match reference dao
     */
    @Bean
    public CommonDao<MatchReference> matchReferenceDao() {
        return MongoDaoProvider.getMatchReferenceDao(databaseDaoUrl);
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public CommonDao<RankedStatsDto> rankedStatsDao() {
        return MongoDaoProvider.getRankedStatsDao(databaseDaoUrl);
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public CommonDao<RecentGamesDto> recentGamesDao() {
        return MongoDaoProvider.getRecentGamesDao(databaseDaoUrl);
    }

    /**
     * Get SummonerDao.
     *
     * @return the summoner dao
     */
    @Bean
    public CommonDao<SummonerDto> summonerDao() {
        return MongoDaoProvider.getSummonerDao(databaseDaoUrl);
    }

    /**
     * Get SummonerDao.
     *
     * @return the summoner dao
     */
    @Bean
    public QueryDao queryDao() {
        return MongoDaoProvider.getQueryDao(databaseDaoUrl);
    }
}
