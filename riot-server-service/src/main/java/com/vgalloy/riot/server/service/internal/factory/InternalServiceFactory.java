package com.vgalloy.riot.server.service.internal.factory;

import java.util.Timer;

import com.vgalloy.riot.server.dao.api.factory.DaoFactory;
import com.vgalloy.riot.server.loader.api.factory.LoaderFactory;
import com.vgalloy.riot.server.service.api.service.ChampionService;
import com.vgalloy.riot.server.service.api.service.ItemService;
import com.vgalloy.riot.server.service.api.service.MatchDetailService;
import com.vgalloy.riot.server.service.api.service.RankedStatsService;
import com.vgalloy.riot.server.service.api.service.SummonerService;
import com.vgalloy.riot.server.service.internal.service.ChampionServiceImpl;
import com.vgalloy.riot.server.service.internal.service.ItemServiceImpl;
import com.vgalloy.riot.server.service.internal.service.MatchDetailServiceImpl;
import com.vgalloy.riot.server.service.internal.service.RankedStatsServiceImpl;
import com.vgalloy.riot.server.service.internal.service.SummonerServiceImpl;
import com.vgalloy.riot.server.service.internal.task.PrivilegedLoaderTask;
import com.vgalloy.riot.server.service.internal.task.SummonerLoaderTask;

/**
 * Created by Vincent Galloy on 11/10/16.
 *
 * @author Vincent Galloy
 */
public final class InternalServiceFactory {

    private static final ChampionService CHAMPION_SERVICE;
    private static final ItemService ITEM_SERVICE;
    private static final MatchDetailService MATCH_DETAIL_SERVICE;
    private static final SummonerService SUMMONER_SERVICE;
    private static final RankedStatsService RANKED_STATS_SERVICE;

    static {
        Timer timer = new Timer();
        timer.schedule(new SummonerLoaderTask(DaoFactory.getSummonerDao(), LoaderFactory.getLoaderClient()), 0, 60_000);
        timer.schedule(new PrivilegedLoaderTask(LoaderFactory.getLoaderClient()), 0, 3600_000);

        CHAMPION_SERVICE = new ChampionServiceImpl(DaoFactory.getChampionDao(), LoaderFactory.getLoaderClient());
        ITEM_SERVICE = new ItemServiceImpl(DaoFactory.getItemDao(), LoaderFactory.getLoaderClient());
        MATCH_DETAIL_SERVICE = new MatchDetailServiceImpl(DaoFactory.getMatchDetailDao());
        SUMMONER_SERVICE = new SummonerServiceImpl(DaoFactory.getSummonerDao(), DaoFactory.getMatchDetailDao(), LoaderFactory.getLoaderClient());
        RANKED_STATS_SERVICE = new RankedStatsServiceImpl(DaoFactory.getRankedStatsDao());
    }

    /**
     * Constructor.
     * To prevent instantiation
     */
    private InternalServiceFactory() {
        throw new AssertionError();
    }

    public static ChampionService getChampionService() {
        return CHAMPION_SERVICE;
    }

    public static ItemService getItemService() {
        return ITEM_SERVICE;
    }

    public static MatchDetailService getMatchDetailService() {
        return MATCH_DETAIL_SERVICE;
    }

    public static SummonerService getSummonerService() {
        return SUMMONER_SERVICE;
    }

    public static RankedStatsService getRankedStatsService() {
        return RANKED_STATS_SERVICE;
    }
}
