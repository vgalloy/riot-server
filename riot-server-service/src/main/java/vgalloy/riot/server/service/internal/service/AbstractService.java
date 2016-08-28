package vgalloy.riot.server.service.internal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.CommonService;
import vgalloy.riot.server.service.internal.service.mapper.ModelMapper;

/**
 * @author Created by Vincent Galloy on 23/08/16.
 */
public abstract class AbstractService<DTO> implements CommonService<DTO> {

    @Autowired
    protected CommonDao<DTO> commonDao;

    @Override
    public Optional<Model<DTO>> get(Region region, Long itemId) {
        Optional<Entity<DTO>> result = commonDao.get(region, itemId);
        if (result.isPresent()) {
            return Optional.of(ModelMapper.map(result.get()));
        }
        return Optional.empty();
    }
}
