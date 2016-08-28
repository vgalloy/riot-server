package vgalloy.riot.server.dao.api.factory;

import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchReferenceDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RankedStatsDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RecentGamesDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.SummonerDaoImpl;
import vgalloy.riot.server.dao.internal.dao.factory.DaoFactory;
import vgalloy.riot.server.dao.internal.dao.query.impl.QueryDaoImpl;

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
     * Get the matchDetailDao.
     *
     * @param databaseUrl the database url
     * @return the matchDetailDao
     */
    public static MatchDetailDao getMatchDetailDao(String databaseUrl) {
        return DaoFactory.getDao(MatchDetailDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the MatchReferenceDao.
     *
     * @param databaseUrl the database url
     * @return the matchReferenceDao
     */
    public static MatchReferenceDao getMatchReferenceDao(String databaseUrl) {
        return DaoFactory.getDao(MatchReferenceDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the rankedStatsDao.
     *
     * @param databaseUrl the database url
     * @return the rankedStatsDao
     */
    public static RankedStatsDao getRankedStatsDao(String databaseUrl) {
        return DaoFactory.getDao(RankedStatsDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the RecentGamesDao.
     *
     * @param databaseUrl the database url
     * @return the RecentGamesDao
     */
    public static RecentGamesDao getRecentGamesDao(String databaseUrl) {
        return DaoFactory.getDao(RecentGamesDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the SummonerDao.
     *
     * @param databaseUrl the database url
     * @return the SummonerDao
     */
    public static SummonerDao getSummonerDao(String databaseUrl) {
        return DaoFactory.getDao(SummonerDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the queryDao.
     *
     * @param databaseUrl the database url
     * @return the queryDao
     */
    public static QueryDao getQueryDao(String databaseUrl) {
        return DaoFactory.getDao(QueryDaoImpl.class, databaseUrl, "riot");
    }
}
