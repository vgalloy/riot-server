package com.vgalloy.riot.server.service.internal.service;

import com.vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import com.vgalloy.riot.server.service.api.model.summoner.RankedStats;
import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;
import com.vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import com.vgalloy.riot.server.service.api.service.RankedStatsService;

/**
 * Created by Vincent Galloy on 23/08/16.
 *
 * @author Vincent Galloy
 */
public final class RankedStatsServiceImpl implements RankedStatsService {

    private final RankedStatsDao rankedStatsDao;

    /**
     * Constructor.
     *
     * @param rankedStatsDao the ranked stats dao
     */
    public RankedStatsServiceImpl(RankedStatsDao rankedStatsDao) {
        this.rankedStatsDao = rankedStatsDao;
    }

    @Override
    public ResourceWrapper<RankedStats> get(SummonerId summonerId) {
        return rankedStatsDao.get(new CommonDpoId(summonerId.getRegion(), summonerId.getId()))
            .map(Entity::getItem)
            .map(e -> e.map(i -> new RankedStats(summonerId, i.getChampions(), i.getModifyDate()))
                .map(ResourceWrapper::of)
                .orElseGet(ResourceWrapper::doesNotExist))
            .orElseGet(ResourceWrapper::notLoaded);
    }
}
