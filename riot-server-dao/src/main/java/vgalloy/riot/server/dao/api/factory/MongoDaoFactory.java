package vgalloy.riot.server.dao.api.factory;

import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.factory.InternalMongoDaoFactory;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 */
public final class MongoDaoFactory {

    /**
     * Constructor.
     * Prevent instantiation
     */
    private MongoDaoFactory() {
        throw new AssertionError();
    }

    /**
     * Get the championDao.
     *
     * @return the championDao
     */
    public static ChampionDao getChampionDao() {
        return InternalMongoDaoFactory.getChampionDao();
    }

    /**
     * Get the matchDetailDao.
     *
     * @return the matchDetailDao
     */
    public static MatchDetailDao getMatchDetailDao() {
        return InternalMongoDaoFactory.getMatchDetailDao();
    }

    /**
     * Get the MatchReferenceDao.
     *
     * @return the matchReferenceDao
     */
    public static MatchReferenceDao getMatchReferenceDao() {
        return InternalMongoDaoFactory.getMatchReferenceDao();
    }

    /**
     * Get the rankedStatsDao.
     *
     * @return the rankedStatsDao
     */
    public static RankedStatsDao getRankedStatsDao() {
        return InternalMongoDaoFactory.getRankedStatsDao();
    }

    /**
     * Get the RecentGamesDao.
     *
     * @return the RecentGamesDao
     */
    public static RecentGamesDao getRecentGamesDao() {
        return InternalMongoDaoFactory.getRecentGamesDao();
    }

    /**
     * Get the SummonerDao.
     *
     * @return the SummonerDao
     */
    public static SummonerDao getSummonerDao() {
        return InternalMongoDaoFactory.getSummonerDao();
    }

    /**
     * Get the ItemDao.
     *
     * @return the ItemDao
     */
    public static ItemDao getItemDao() {
        return InternalMongoDaoFactory.getItemDao();
    }

    /**
     * Get the queryDao.
     *
     * @return the queryDao
     */
    public static QueryDao getQueryDao() {
        return InternalMongoDaoFactory.getQueryDao();
    }
}
