package vgalloy.riot.server.service.api.factory;

import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.service.internal.factory.ContextFactory;
import vgalloy.riot.server.service.internal.factory.InternalServiceFactory;
import vgalloy.riot.server.service.internal.loader.context.ContextManager;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 11/10/16.
 */
public final class ServiceFactory {

    /**
     * Constructor.
     *
     * To prevent instantiation
     */
    private ServiceFactory() {
        throw new AssertionError();
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

    /**
     * Get the QueryService.
     *
     * @return the QueryService
     */
    public static QueryService getQueryService() {
        return InternalServiceFactory.getQueryService();
    }

    /**
     * Get the ContextManager.
     *
     * @return the ContextManager
     */
    public static ContextManager getContextManager() {
        return ContextFactory.getContextManager();
    }
}
