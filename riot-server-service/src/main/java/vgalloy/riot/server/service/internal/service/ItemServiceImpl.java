package vgalloy.riot.server.service.internal.service;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.ItemService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class ItemServiceImpl extends AbstractService<ItemDto> implements ItemService {

    private final LoaderClient loaderClient;

    /**
     * Constructor.
     *
     * @param itemDao      the item dao
     * @param loaderClient the summoner loader client
     */
    public ItemServiceImpl(ItemDao itemDao, LoaderClient loaderClient) {
        super(itemDao);
        this.loaderClient = Objects.requireNonNull(loaderClient);
    }

    @Override
    public Optional<Model<ItemDto>> get(ItemId itemId) {
        loaderClient.loadAsyncItemById(itemId.getRegion(), Math.toIntExact(itemId.getId()));
        return super.get(itemId);
    }
}
