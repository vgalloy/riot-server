package vgalloy.riot.server.loader.internal.loader.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.loader.internal.executor.Executor;
import vgalloy.riot.server.loader.internal.helper.RegionPrinter;
import vgalloy.riot.server.loader.internal.loader.ItemLoader;

/**
 * @author Vincent Galloy - 01/12/16
 *         Created by Vincent Galloy on 01/12/16.
 */
public class ItemLoaderImpl implements ItemLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemLoaderImpl.class);

    private final RiotApi riotApi;
    private final Executor executor;
    private final ItemDao itemDao;

    /**
     * Constructor.
     *
     * @param riotApi  the riot api
     * @param executor the executor
     * @param itemDao  the item dao
     */
    public ItemLoaderImpl(RiotApi riotApi, Executor executor, ItemDao itemDao) {
        this.riotApi = Objects.requireNonNull(riotApi);
        this.executor = Objects.requireNonNull(executor);
        this.itemDao = Objects.requireNonNull(itemDao);
    }

    @Override
    public void loadItemById(Region region, Integer itemId) {
        Objects.requireNonNull(itemId);
        LOGGER.info("{} load item with id : {}", RegionPrinter.getRegion(region), itemId);
        ItemId item = new ItemId(region, (long) itemId);
        if (shouldIdLoadThisItem(item)) {
            ItemDto itemDto = executor.execute(riotApi.getItemById(item.getId()), item.getRegion(), 1);
            itemDao.save(new CommonWrapper<>(item, itemDto));
        }
    }

    /**
     * Check if the item is ok for loading.
     *
     * @param itemId the item id
     * @return true if the item can be loaded
     */
    private boolean shouldIdLoadThisItem(ItemId itemId) {
        Optional<Entity<CommonWrapper<ItemDto>>> optionalEntity = itemDao.get(itemId);
        if (!optionalEntity.isPresent()) {
            return true;
        }
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(optionalEntity.get().getLastUpdate(), 0, ZoneOffset.UTC);
        if (localDateTime.isBefore(LocalDateTime.now().minus(10, ChronoUnit.DAYS))) {
            return true;
        }
        return false;
    }
}
