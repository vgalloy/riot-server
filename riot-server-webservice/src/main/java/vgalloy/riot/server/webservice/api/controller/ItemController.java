package vgalloy.riot.server.webservice.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.service.api.service.ItemService;
import vgalloy.riot.server.webservice.internal.model.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.model.ResourceNotLoadedException;

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
    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public ItemDto getItemById(@PathVariable Long itemId, @RequestParam(required = false) Region region) {
        LOGGER.info("[ GET ] : getItemById, itemId : {}, region : {}", itemId, region);
        Region computedRegion = Optional.ofNullable(region).orElse(Region.EUW);
        return itemService.get(new DpoId(computedRegion, itemId))
                .ifNotLoadedThrow(ResourceNotLoadedException::new)
                .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }
}
