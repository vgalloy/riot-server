package vgalloy.riot.server.dao.api.factory;

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
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.ChampionDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.ItemDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchReferenceDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RankedStatsDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RecentGamesDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.SummonerDaoImpl;
import vgalloy.riot.server.dao.internal.dao.query.impl.QueryDaoImpl;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 */
public final class MongoDaoFactory {

    private static final ChampionDao CHAMPION_DAO;
    private static final MatchDetailDao MATCH_DETAIL_DAO;
    private static final MatchReferenceDao MATCH_REFERENCE_DAO;
    private static final RankedStatsDao RANKED_STATS_DAO;
    private static final RecentGamesDao RECENT_GAMES_DAO;
    private static final SummonerDao SUMMONER_DAO;
    private static final ItemDao ITEM_DAO;
    private static final QueryDao QUERY_DAO;

    static {
        try {
            Configuration configuration = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName("application.properties"))
                    .getConfiguration();
            String databaseUrl = (String) configuration.getProperty("database.url");
            String databaseName = (String) configuration.getProperty("database.name");

            CHAMPION_DAO = new ChampionDaoImpl(databaseUrl, databaseName);
            MATCH_DETAIL_DAO = new MatchDetailDaoImpl(databaseUrl, databaseName);
            MATCH_REFERENCE_DAO = new MatchReferenceDaoImpl(databaseUrl, databaseName);
            RANKED_STATS_DAO = new RankedStatsDaoImpl(databaseUrl, databaseName);
            RECENT_GAMES_DAO = new RecentGamesDaoImpl(databaseUrl, databaseName);
            SUMMONER_DAO = new SummonerDaoImpl(databaseUrl, databaseName);
            QUERY_DAO = new QueryDaoImpl(databaseUrl, databaseName);
            ITEM_DAO = new ItemDaoImpl(databaseUrl, databaseName);
        } catch (ConfigurationException e) {
            throw new MongoDaoException("Unable to load configuration", e);
        }
    }

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
     * Get the queryDao.
     *
     * @return the queryDao
     */
    public static QueryDao getQueryDao() {
        return QUERY_DAO;
    }
}
