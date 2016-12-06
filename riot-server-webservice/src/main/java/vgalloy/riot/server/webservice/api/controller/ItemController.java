package vgalloy.riot.server.webservice.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.ItemService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
@RestController
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    /**
     * Get the item information.
     *
     * @param region the region
     * @param itemId the item id
     * @return the item information
     */
    @RequestMapping(value = "/item/{region}/{itemId}", method = RequestMethod.GET)
    public ItemDto getItemById(@PathVariable Region region, @PathVariable Long itemId) {
        LOGGER.info("[ GET ] : getItemById, region : {}, itemId : {}", region, itemId);
        Optional<Model<ItemDto>> result = itemService.get(new ItemId(region, itemId));
        if (result.isPresent()) {
            return result.get().getItem();
        }
        return null;
    }
}