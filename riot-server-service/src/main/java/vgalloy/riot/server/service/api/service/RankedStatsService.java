package vgalloy.riot.server.service.api.service;

import vgalloy.riot.server.service.api.model.summoner.RankedStats;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/06/16.
 */
public interface RankedStatsService {

    /**
     * Get the rankedStat.
     *
     * @param summonerId the summoner id
     * @return the rankedStat
     */
    ResourceWrapper<RankedStats> get(SummonerId summonerId);
}
