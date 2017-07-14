package com.vgalloy.riot.server.service.api.service;

import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;

import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
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
