package vgalloy.riot.server.service.internal.service;

import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.service.api.model.summoner.RankedStats;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import vgalloy.riot.server.service.api.service.RankedStatsService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
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
        return rankedStatsDao.get(new DpoId(summonerId.getRegion(), summonerId.getId()))
                .map(Entity::getItem)
                .map(e -> e.map(i -> new RankedStats(summonerId, i.getChampions(), i.getModifyDate()))
                        .map(ResourceWrapper::of)
                        .orElseGet(ResourceWrapper::doesNotExist))
                .orElseGet(ResourceWrapper::notLoaded);
    }
}
