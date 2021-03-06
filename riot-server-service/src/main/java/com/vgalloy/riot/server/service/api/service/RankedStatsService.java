package com.vgalloy.riot.server.service.api.service;

import com.vgalloy.riot.server.service.api.model.summoner.RankedStats;
import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;
import com.vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;

/**
 * Created by Vincent Galloy on 23/06/16.
 *
 * @author Vincent Galloy
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
