package vgalloy.riot.server.dao.internal.elasticsearch.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class ItemElasticsearchDaoImpl implements ItemDao {

    @Override
    public void save(CommonDpoWrapper<ItemDto> itemWrapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<ItemDto, DpoId>> get(DpoId dpoId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Entity<ItemDto, DpoId>> getRandom(Region region) {
        throw new UnsupportedOperationException();
    }
}
