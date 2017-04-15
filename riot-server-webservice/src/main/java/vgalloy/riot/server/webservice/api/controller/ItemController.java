package vgalloy.riot.server.webservice.api.controller;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;

/**
 * Created by Vincent Galloy on 13/06/16.
 *
 * @author Vincent Galloy
 */
public interface ItemController {

    /**
     * Get the item information.
     *
     * @param region the region
     * @param itemId the item id
     * @return the item information
     */
    ItemDto getItemById(Long itemId, Region region);
}
