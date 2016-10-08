package vgalloy.riot.server.service.internal.service;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.CommonService;
import vgalloy.riot.server.service.internal.service.mapper.ModelMapper;

/**
 * @author Created by Vincent Galloy on 23/08/16.
 */
public abstract class AbstractService<DTO> implements CommonService<DTO> {

    private final CommonDao<DTO> commonDao;

    /**
     * Constructor.
     *
     * @param commonDao the common dao
     */
    protected AbstractService(CommonDao<DTO> commonDao) {
        this.commonDao = Objects.requireNonNull(commonDao);
    }

    @Override
    public Optional<Model<DTO>> get(ItemId itemId) {
        Optional<Entity<CommonWrapper<DTO>>> result = commonDao.get(itemId);
        if (result.isPresent()) {
            return Optional.of(ModelMapper.map(result.get()));
        }
        return Optional.empty();
    }
}
