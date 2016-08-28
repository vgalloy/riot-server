package vgalloy.riot.server.dao.api.entity;

import vgalloy.riot.api.api.constant.Region;

import java.io.Serializable;
import java.util.Objects;


/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class Entity<DTO> implements Serializable {

    private final Long lastUpdate;
    private final Region region;
    private final Long itemId;
    private final DTO item;

    /**
     * Constructor.
     *
     * @param region     the region
     * @param itemId     the item id
     * @param item       the item
     * @param lastUpdate the last update
     */
    public Entity(Region region, Long itemId, DTO item, Long lastUpdate) {
        this.lastUpdate = Objects.requireNonNull(lastUpdate, "lastUpdate can not be null");
        this.region = Objects.requireNonNull(region, "region can not be null");
        this.itemId = Objects.requireNonNull(itemId, "itemId can not be null");
        this.item = Objects.requireNonNull(item, "item can not be null");
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public Region getRegion() {
        return region;
    }

    public Long getItemId() {
        return itemId;
    }

    public DTO getItem() {
        return item;
    }
}
