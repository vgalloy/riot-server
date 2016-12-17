package vgalloy.riot.server.dao.api.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public interface CommonDao<DTO> {

    /**
     * Save the dto.
     *
     * @param itemWrapper the item wrapper
     */
    void save(CommonDpoWrapper<DTO> itemWrapper);

    /**
     * Get the entity.
     *
     * @param dpoId the item id
     * @return the datable
     */
    Optional<Entity<DTO, DpoId>> get(DpoId dpoId);

    /**
     * Get one random element in the collection.
     *
     * @param region the region
     * @return the random element
     */
    Optional<Entity<DTO, DpoId>> getRandom(Region region);
}
