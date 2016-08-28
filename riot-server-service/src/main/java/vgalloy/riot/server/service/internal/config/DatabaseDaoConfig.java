package vgalloy.riot.server.service.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vgalloy.riot.api.api.dto.game.RecentGamesDto;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;

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
        return MongoDaoFactory.getMatchDetailDao(databaseDaoUrl);
    }

    /**
     * Get MatchReferenceDao.
     *
     * @return the match reference dao
     */
    @Bean
    public CommonDao<MatchReference> matchReferenceDao() {
        return MongoDaoFactory.getMatchReferenceDao(databaseDaoUrl);
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public CommonDao<RankedStatsDto> rankedStatsDao() {
        return MongoDaoFactory.getRankedStatsDao(databaseDaoUrl);
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public CommonDao<RecentGamesDto> recentGamesDao() {
        return MongoDaoFactory.getRecentGamesDao(databaseDaoUrl);
    }

    /**
     * Get SummonerDao.
     *
     * @return the summoner dao
     */
    @Bean
    public CommonDao<SummonerDto> summonerDao() {
        return MongoDaoFactory.getSummonerDao(databaseDaoUrl);
    }

    /**
     * Get SummonerDao.
     *
     * @return the summoner dao
     */
    @Bean
    public QueryDao queryDao() {
        return MongoDaoFactory.getQueryDao(databaseDaoUrl);
    }
}
