package vgalloy.riot.server.service.api.service;

import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 03/12/16.
 */
public interface ItemService {

    /**
     * Get the item.
     *
     * @param dpoId the item id
     * @return the item
     */
    ResourceWrapper<ItemDto> get(DpoId dpoId);
}
