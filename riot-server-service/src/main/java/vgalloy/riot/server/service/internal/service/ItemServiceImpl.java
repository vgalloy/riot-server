package vgalloy.riot.server.service.internal.service;

import java.util.Objects;

import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import vgalloy.riot.server.service.api.service.ItemService;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public final class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final LoaderClient loaderClient;

    /**
     * Constructor.
     *
     * @param itemDao      the item dao
     * @param loaderClient the summoner loader client
     */
    public ItemServiceImpl(ItemDao itemDao, LoaderClient loaderClient) {
        this.itemDao = Objects.requireNonNull(itemDao);
        this.loaderClient = Objects.requireNonNull(loaderClient);
    }

    @Override
    public ResourceWrapper<ItemDto> get(DpoId dpoId) {
        loaderClient.loadAsyncItemById(dpoId.getRegion(), Math.toIntExact(dpoId.getId()));
        return itemDao.get(dpoId)
            .map(Entity::getItem)
            .map(e -> e.map(ResourceWrapper::of)
                .orElseGet(ResourceWrapper::doesNotExist))
            .orElseGet(ResourceWrapper::notLoaded);
    }
}
