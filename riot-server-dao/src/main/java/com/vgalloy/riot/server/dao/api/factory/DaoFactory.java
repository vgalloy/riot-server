package com.vgalloy.riot.server.dao.api.factory;

import com.vgalloy.riot.server.dao.api.dao.ChampionDao;
import com.vgalloy.riot.server.dao.api.dao.ItemDao;
import com.vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import com.vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import com.vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import com.vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import com.vgalloy.riot.server.dao.api.dao.SummonerDao;
import com.vgalloy.riot.server.dao.internal.factory.InternalMongoDaoFactory;

/**
 * Created by Vincent Galloy on 09/06/16.
 *
 * @author Vincent Galloy
 */
public final class DaoFactory {

    /**
     * Constructor.
     * Prevent instantiation
     */
    private DaoFactory() {
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
        return InternalMongoDaoFactory.getGlobalMatchDetailDao();
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
}
