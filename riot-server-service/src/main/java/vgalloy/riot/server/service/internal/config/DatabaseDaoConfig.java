package vgalloy.riot.server.service.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
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
    public MatchDetailDao matchDetailDao() {
        return MongoDaoFactory.getMatchDetailDao(databaseDaoUrl);
    }

    /**
     * Get MatchReferenceDao.
     *
     * @return the match reference dao
     */
    @Bean
    public MatchReferenceDao matchReferenceDao() {
        return MongoDaoFactory.getMatchReferenceDao(databaseDaoUrl);
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public RankedStatsDao rankedStatsDao() {
        return MongoDaoFactory.getRankedStatsDao(databaseDaoUrl);
    }

    /**
     * Get RankedStatsDao.
     *
     * @return the ranked stats dao
     */
    @Bean
    public RecentGamesDao recentGamesDao() {
        return MongoDaoFactory.getRecentGamesDao(databaseDaoUrl);
    }

    /**
     * Get SummonerDao.
     *
     * @return the summoner dao
     */
    @Bean
    public SummonerDao summonerDao() {
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
