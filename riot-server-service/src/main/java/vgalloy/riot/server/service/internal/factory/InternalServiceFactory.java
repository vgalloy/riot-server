package vgalloy.riot.server.service.internal.factory;

import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.service.internal.service.MatchDetailServiceImpl;
import vgalloy.riot.server.service.internal.service.QueryServiceImpl;
import vgalloy.riot.server.service.internal.service.RankedStatsServiceImpl;
import vgalloy.riot.server.service.internal.service.SummonerServiceImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 11/10/16.
 */
public final class InternalServiceFactory {

    private static final MatchDetailService MATCH_DETAIL_SERVICE = new MatchDetailServiceImpl(MongoDaoFactory.getMatchDetailDao());
    private static final SummonerService SUMMONER_SERVICE = new SummonerServiceImpl(MongoDaoFactory.getSummonerDao(), MongoDaoFactory.getMatchDetailDao(), BrokerFactory.getSummonerLoaderClient());
    private static final RankedStatsService RANKED_STATS_SERVICE = new RankedStatsServiceImpl(MongoDaoFactory.getRankedStatsDao());
    private static final QueryService QUERY_SERVICE = new QueryServiceImpl(MongoDaoFactory.getQueryDao(), MongoDaoFactory.getMatchDetailDao());

    /**
     * Constructor.
     * To prevent instantiation
     */
    private InternalServiceFactory() {
        throw new AssertionError();
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

    public static QueryService getQueryService() {
        return QUERY_SERVICE;
    }
}
