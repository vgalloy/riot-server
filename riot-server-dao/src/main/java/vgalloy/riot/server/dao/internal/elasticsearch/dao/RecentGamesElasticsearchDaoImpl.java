package vgalloy.riot.server.dao.internal.elasticsearch.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.game.RecentGamesDto;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class RecentGamesElasticsearchDaoImpl implements RecentGamesDao {

    @Override
    public void save(CommonDpoWrapper<RecentGamesDto> dpoWrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<RecentGamesDto, DpoId>> get(DpoId dpoId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<RecentGamesDto, DpoId>> getRandom(Region region) {
        throw new UnsupportedOperationException();
    }
}
