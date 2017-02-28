package vgalloy.riot.server.service.api.factory;

import vgalloy.riot.server.service.api.service.ChampionService;
import vgalloy.riot.server.service.api.service.ItemService;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.service.internal.factory.InternalServiceFactory;

/**
 * Created by Vincent Galloy on 11/10/16.
 *
 * @author Vincent Galloy
 */
public final class ServiceFactory {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ServiceFactory() {
        throw new AssertionError();
    }

    /**
     * Get the ChampionService.
     *
     * @return the ChampionService
     */
    public static ChampionService getChampionService() {
        return InternalServiceFactory.getChampionService();
    }

    /**
     * Get the ItemService.
     *
     * @return the ItemService
     */
    public static ItemService getItemService() {
        return InternalServiceFactory.getItemService();
    }

    /**
     * Get the MatchDetailService.
     *
     * @return the MatchDetailService
     */
    public static MatchDetailService getMatchDetailService() {
        return InternalServiceFactory.getMatchDetailService();
    }

    /**
     * Get the SummonerService.
     *
     * @return the SummonerService
     */
    public static SummonerService getSummonerService() {
        return InternalServiceFactory.getSummonerService();
    }

    /**
     * Get the RankedStatsService.
     *
     * @return the RankedStatsService
     */
    public static RankedStatsService getRankedStatsService() {
        return InternalServiceFactory.getRankedStatsService();
    }
}
