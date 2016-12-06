package vgalloy.riot.server.dao.api.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;

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
    void save(CommonWrapper<DTO> itemWrapper);

    /**
     * Get the entity.
     *
     * @param itemId the item id
     * @return the datable
     */
    Optional<Entity<DTO, ItemId>> get(ItemId itemId);

    /**
     * Get one random element in the collection.
     *
     * @param region the region
     * @return the random element
     */
    Optional<Entity<DTO, ItemId>> getRandom(Region region);
}
