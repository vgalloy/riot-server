package vgalloy.riot.server.service.internal.service;

import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.service.api.service.RankedStatsService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class RankedStatsServiceImpl extends AbstractService<RankedStatsDto> implements RankedStatsService {

    /**
     * Constructor.
     *
     * @param rankedStatsDao the ranked stats dao
     */
    public RankedStatsServiceImpl(RankedStatsDao rankedStatsDao) {
        super(rankedStatsDao);
    }
}
