package vgalloy.riot.server.service.internal.service;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
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
    public Optional<Model<DTO>> get(DpoId dpoId) {
        Optional<Entity<DTO, DpoId>> result = commonDao.get(dpoId);
        if (result.isPresent()) {
            return Optional.of(ModelMapper.map(result.get()));
        }
        return Optional.empty();
    }
}
