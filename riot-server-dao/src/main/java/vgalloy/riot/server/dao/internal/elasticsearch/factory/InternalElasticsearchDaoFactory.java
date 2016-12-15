package vgalloy.riot.server.dao.internal.elasticsearch.factory;

import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.elasticsearch.dao.ChampionElasticsearchDaoImpl;
import vgalloy.riot.server.dao.internal.elasticsearch.dao.ItemElasticsearchDaoImpl;
import vgalloy.riot.server.dao.internal.elasticsearch.dao.MatchDetailElasticsearchDaoImpl;
import vgalloy.riot.server.dao.internal.elasticsearch.dao.MatchReferenceElasticsearchDaoImpl;
import vgalloy.riot.server.dao.internal.elasticsearch.dao.RankedStatsElasticsearchDaoImpl;
import vgalloy.riot.server.dao.internal.elasticsearch.dao.RecentGamesElasticsearchDaoImpl;
import vgalloy.riot.server.dao.internal.elasticsearch.dao.SummonerElasticsearchDaoImpl;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class InternalElasticsearchDaoFactory {

    private static final ChampionDao CHAMPION_DAO;
    private static final MatchDetailDao MATCH_DETAIL_DAO;
    private static final MatchReferenceDao MATCH_REFERENCE_DAO;
    private static final RankedStatsDao RANKED_STATS_DAO;
    private static final RecentGamesDao RECENT_GAMES_DAO;
    private static final SummonerDao SUMMONER_DAO;
    private static final ItemDao ITEM_DAO;

    static {
        CHAMPION_DAO = new ChampionElasticsearchDaoImpl();
        MATCH_DETAIL_DAO = new MatchDetailElasticsearchDaoImpl();
        MATCH_REFERENCE_DAO = new MatchReferenceElasticsearchDaoImpl();
        RANKED_STATS_DAO = new RankedStatsElasticsearchDaoImpl();
        RECENT_GAMES_DAO = new RecentGamesElasticsearchDaoImpl();
        SUMMONER_DAO = new SummonerElasticsearchDaoImpl();
        ITEM_DAO = new ItemElasticsearchDaoImpl();
    }

    /**
     * Constructor.
     * Prevent instantiation
     */
    private InternalElasticsearchDaoFactory() {
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
}
