package vgalloy.riot.server.dao.internal.elasticsearch.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class MatchReferenceElasticsearchDaoImpl implements MatchReferenceDao {

    @Override
    public void save(CommonDpoWrapper<MatchReference> dpoWrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<MatchReference, DpoId>> get(DpoId dpoId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<MatchReference, DpoId>> getRandom(Region region) {
        throw new UnsupportedOperationException();
    }
}
