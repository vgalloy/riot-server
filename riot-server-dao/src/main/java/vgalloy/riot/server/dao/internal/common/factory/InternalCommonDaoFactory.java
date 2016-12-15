package vgalloy.riot.server.dao.internal.common.factory;

import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.elasticsearch.factory.InternalElasticsearchDaoFactory;
import vgalloy.riot.server.dao.internal.mongo.factory.InternalMongoDaoFactory;

/**
 * @author Vincent Galloy - 15/12/16
 *         Created by Vincent Galloy on 15/12/16.
 */
public final class InternalCommonDaoFactory {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private InternalCommonDaoFactory() {
        throw new AssertionError();
    }

    /**
     * Get the championDao.
     *
     * @return the championDao
     */
    public static ChampionDao getChampionDao() {
        return ProxyFactory.create(ChampionDao.class, InternalMongoDaoFactory.getChampionDao(), InternalElasticsearchDaoFactory.getChampionDao());
    }

    /**
     * Get the matchDetailDao.
     *
     * @return the matchDetailDao
     */
    public static MatchDetailDao getMatchDetailDao() {
        return ProxyFactory.create(MatchDetailDao.class, InternalMongoDaoFactory.getGlobalMatchDetailDao(), InternalElasticsearchDaoFactory.getMatchDetailDao());
    }

    /**
     * Get the MatchReferenceDao.
     *
     * @return the matchReferenceDao
     */
    public static MatchReferenceDao getMatchReferenceDao() {
        return ProxyFactory.create(MatchReferenceDao.class, InternalMongoDaoFactory.getMatchReferenceDao(), InternalElasticsearchDaoFactory.getMatchReferenceDao());
    }

    /**
     * Get the rankedStatsDao.
     *
     * @return the rankedStatsDao
     */
    public static RankedStatsDao getRankedStatsDao() {
        return ProxyFactory.create(RankedStatsDao.class, InternalMongoDaoFactory.getRankedStatsDao(), InternalElasticsearchDaoFactory.getRankedStatsDao());
    }

    /**
     * Get the RecentGamesDao.
     *
     * @return the RecentGamesDao
     */
    public static RecentGamesDao getRecentGamesDao() {
        return ProxyFactory.create(RecentGamesDao.class, InternalMongoDaoFactory.getRecentGamesDao(), InternalElasticsearchDaoFactory.getRecentGamesDao());
    }

    /**
     * Get the SummonerDao.
     *
     * @return the SummonerDao
     */
    public static SummonerDao getSummonerDao() {
        return ProxyFactory.create(SummonerDao.class, InternalMongoDaoFactory.getSummonerDao(), InternalElasticsearchDaoFactory.getSummonerDao());
    }

    /**
     * Get the ItemDao.
     *
     * @return the ItemDao
     */
    public static ItemDao getItemDao() {
        return ProxyFactory.create(ItemDao.class, InternalMongoDaoFactory.getItemDao(), InternalElasticsearchDaoFactory.getItemDao());
    }
}
