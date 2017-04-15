package vgalloy.riot.server.webservice.internal.controller.impl;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import vgalloy.riot.server.service.api.service.ItemService;
import vgalloy.riot.server.webservice.api.controller.ItemController;
import vgalloy.riot.server.webservice.internal.model.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.model.ResourceNotLoadedException;

/**
 * Created by Vincent Galloy on 13/06/16.
 *
 * @author Vincent Galloy
 */
@RestController
public class ItemControllerImpl implements ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemControllerImpl.class);

    private final ItemService itemService;

    /**
     * Constructor.
     *
     * @param itemService the itemService
     */
    public ItemControllerImpl(ItemService itemService) {
        this.itemService = Objects.requireNonNull(itemService);
    }

    @Override
    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public ItemDto getItemById(@PathVariable Long itemId, @RequestParam(required = false) Region region) {
        LOGGER.info("[ GET ] : getItemById, itemId : {}, region : {}", itemId, region);
        Region computedRegion = Optional.ofNullable(region).orElse(Region.EUW);
        return itemService.get(new CommonDpoId(computedRegion, itemId))
            .ifNotLoadedThrow(ResourceNotLoadedException::new)
            .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }
}
