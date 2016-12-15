package vgalloy.riot.server.dao.internal.mongo.factory;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.TimelineDao;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.ChampionMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.ItemMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.MatchReferenceMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.RankedStatsMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.RecentGamesMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.SummonerMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.matchdetail.GlobalMatchDetailMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.matchdetail.MatchDetailMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.matchdetail.TimelineMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.exception.MongoDaoException;

/**
 * @author Vincent Galloy - 15/12/16
 *         Created by Vincent Galloy on 15/12/16.
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
        try {
            Configuration configuration = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName("application.properties"))
                    .getConfiguration();
            String databaseUrl = configuration.getString("database.url");
            String databaseName = configuration.getString("database.name");

            CHAMPION_DAO = new ChampionMongoDaoImpl(databaseUrl, databaseName);
            MATCH_DETAIL_DAO = new MatchDetailMongoDaoImpl(databaseUrl, databaseName);
            MATCH_REFERENCE_DAO = new MatchReferenceMongoDaoImpl(databaseUrl, databaseName);
            RANKED_STATS_DAO = new RankedStatsMongoDaoImpl(databaseUrl, databaseName);
            RECENT_GAMES_DAO = new RecentGamesMongoDaoImpl(databaseUrl, databaseName);
            SUMMONER_DAO = new SummonerMongoDaoImpl(databaseUrl, databaseName);
            ITEM_DAO = new ItemMongoDaoImpl(databaseUrl, databaseName);
            TIMELINE_DAO = new TimelineMongoDaoImpl(databaseUrl, databaseName);
            GLOBAL_MATCH_DETAIL_DAO = new GlobalMatchDetailMongoDaoImpl(TIMELINE_DAO, MATCH_DETAIL_DAO);
        } catch (ConfigurationException e) {
            throw new MongoDaoException("Unable to load configuration", e);
        }
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
