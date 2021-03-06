package com.vgalloy.riot.server.dao.internal.factory;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.vgalloy.riot.server.dao.api.dao.ChampionDao;
import com.vgalloy.riot.server.dao.api.dao.ItemDao;
import com.vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import com.vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import com.vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import com.vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import com.vgalloy.riot.server.dao.api.dao.SummonerDao;
import com.vgalloy.riot.server.dao.api.factory.ConfigurationLoader;
import com.vgalloy.riot.server.dao.internal.dao.TimelineDao;
import com.vgalloy.riot.server.dao.internal.dao.factory.MongoDatabaseFactory;
import com.vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import com.vgalloy.riot.server.dao.internal.dao.impl.ItemDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.MatchReferenceDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.RankedStatsDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.RecentGamesDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.champion.ChampionDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.matchdetail.GlobalMatchDetailDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.matchdetail.MatchDetailDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.matchdetail.TimelineDaoImpl;
import com.vgalloy.riot.server.dao.internal.dao.impl.summoner.SummonerDaoImpl;
import com.vgalloy.riot.server.dao.internal.exception.MongoDaoException;
import com.vgalloy.riot.server.dao.internal.task.factory.TaskFactory;
import com.vgalloy.riot.server.dao.internal.task.impl.DatabaseCleanerTask;
import com.vgalloy.riot.server.dao.internal.task.impl.UpdateWinRateTask;

/**
 * Created by Vincent Galloy on 09/06/16.
 *
 * @author Vincent Galloy
 */
public final class InternalMongoDaoFactory {

    private static final ChampionDao CHAMPION_DAO;
    private static final MatchDetailDao MATCH_DETAIL_DAO;
    private static final MatchReferenceDao MATCH_REFERENCE_DAO;
    private static final RankedStatsDao RANKED_STATS_DAO;
    private static final RecentGamesDao RECENT_GAMES_DAO;
    private static final SummonerDao SUMMONER_DAO;
    private static final ItemDao ITEM_DAO;
    private static final TimelineDao TIMELINE_DAO;
    private static final MatchDetailDao GLOBAL_MATCH_DETAIL_DAO;

    static {
        Configuration configuration;
        try {
            configuration = ConfigurationLoader.loadConfiguration();
        } catch (ConfigurationException e) {
            throw new MongoDaoException(e);
        }
        String databaseUrl = configuration.getString("database.url");
        String databaseName = configuration.getString("database.name");

        CHAMPION_DAO = new ChampionDaoImpl(databaseUrl, databaseName);
        MATCH_DETAIL_DAO = new MatchDetailDaoImpl(databaseUrl, databaseName);
        MATCH_REFERENCE_DAO = new MatchReferenceDaoImpl(databaseUrl, databaseName);
        RANKED_STATS_DAO = new RankedStatsDaoImpl(databaseUrl, databaseName);
        RECENT_GAMES_DAO = new RecentGamesDaoImpl(databaseUrl, databaseName);
        SUMMONER_DAO = new SummonerDaoImpl(databaseUrl, databaseName);
        ITEM_DAO = new ItemDaoImpl(databaseUrl, databaseName);
        TIMELINE_DAO = new TimelineDaoImpl(databaseUrl, databaseName);
        GLOBAL_MATCH_DETAIL_DAO = new GlobalMatchDetailDaoImpl(TIMELINE_DAO, MATCH_DETAIL_DAO);

        MongoDatabaseFactory mongoDatabaseFactory = MongoDriverObjectFactory.getMongoClient(databaseUrl).getMongoDatabase(databaseName);
        TaskFactory.startTask(new UpdateWinRateTask(mongoDatabaseFactory), 15 * 60 * 1000);
        TaskFactory.startTask(new DatabaseCleanerTask(GLOBAL_MATCH_DETAIL_DAO), 24 * 60 * 60 * 1000);
    }

    /**
     * Constructor.
     * Prevent instantiation
     */
    private InternalMongoDaoFactory() {
        throw new AssertionError();
    }

    /**
     * Get the championDao.
     *
     * @return the championDao
     */
    public static ChampionDao getChampionDao() {
        return CHAMPION_DAO;
    }

    /**
     * Get the matchDetailDao.
     *
     * @return the matchDetailDao
     */
    public static MatchDetailDao getMatchDetailDao() {
        return MATCH_DETAIL_DAO;
    }

    /**
     * Get the MatchReferenceDao.
     *
     * @return the matchReferenceDao
     */
    public static MatchReferenceDao getMatchReferenceDao() {
        return MATCH_REFERENCE_DAO;
    }

    /**
     * Get the rankedStatsDao.
     *
     * @return the rankedStatsDao
     */
    public static RankedStatsDao getRankedStatsDao() {
        return RANKED_STATS_DAO;
    }

    /**
     * Get the RecentGamesDao.
     *
     * @return the RecentGamesDao
     */
    public static RecentGamesDao getRecentGamesDao() {
        return RECENT_GAMES_DAO;
    }

    /**
     * Get the SummonerDao.
     *
     * @return the SummonerDao
     */
    public static SummonerDao getSummonerDao() {
        return SUMMONER_DAO;
    }

    /**
     * Get the ItemDao.
     *
     * @return the ItemDao
     */
    public static ItemDao getItemDao() {
        return ITEM_DAO;
    }

    /**
     * Get the TimelineDao.
     *
     * @return the TimelineDao
     */
    public static TimelineDao getTimelineDao() {
        return TIMELINE_DAO;
    }

    /**
     * Get the MatchDetailDao.
     *
     * @return the MatchDetailDao
     */
    public static MatchDetailDao getGlobalMatchDetailDao() {
        return GLOBAL_MATCH_DETAIL_DAO;
    }
}
