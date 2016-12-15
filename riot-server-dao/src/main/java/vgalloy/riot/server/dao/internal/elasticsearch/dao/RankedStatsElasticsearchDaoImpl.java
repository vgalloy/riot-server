package vgalloy.riot.server.dao.internal.elasticsearch.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class RankedStatsElasticsearchDaoImpl implements RankedStatsDao {

    @Override
    public void save(CommonDpoWrapper<RankedStatsDto> itemWrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<RankedStatsDto, DpoId>> get(DpoId dpoId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<RankedStatsDto, DpoId>> getRandom(Region region) {
        throw new UnsupportedOperationException();
    }
}
