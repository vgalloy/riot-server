package vgalloy.riot.server.dao.api.provider;

import vgalloy.riot.api.rest.request.game.dto.RecentGamesDto;
import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchReference;
import vgalloy.riot.api.rest.request.stats.dto.RankedStatsDto;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.dao.CommonDao;
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
public final class MongoDaoProvider {

    /**
     * Constructor.
     * Prevent instantiation
     */
    public MongoDaoProvider() {
        throw new AssertionError();
    }

    /**
     * Get the matchDetailDao.
     *
     * @param databaseUrl the database url
     * @return the matchDetailDao
     */
    public static CommonDao<MatchDetail> getMatchDetailDao(String databaseUrl) {
        return DaoFactory.getDao(MatchDetailDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the MatchReferenceDao.
     *
     * @param databaseUrl the database url
     * @return the matchReferenceDao
     */
    public static CommonDao<MatchReference> getMatchReferenceDao(String databaseUrl) {
        return DaoFactory.getDao(MatchReferenceDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the rankedStatsDao.
     *
     * @param databaseUrl the database url
     * @return the rankedStatsDao
     */
    public static CommonDao<RankedStatsDto> getRankedStatsDao(String databaseUrl) {
        return DaoFactory.getDao(RankedStatsDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the RecentGamesDao.
     *
     * @param databaseUrl the database url
     * @return the RecentGamesDao
     */
    public static CommonDao<RecentGamesDto> getRecentGamesDao(String databaseUrl) {
        return DaoFactory.getDao(RecentGamesDaoImpl.class, databaseUrl, "riot");
    }

    /**
     * Get the SummonerDao.
     *
     * @param databaseUrl the database url
     * @return the SummonerDao
     */
    public static CommonDao<SummonerDto> getSummonerDao(String databaseUrl) {
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
