package vgalloy.riot.server.dao.api.entity;

import java.io.Serializable;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class Entity<DTO> implements Serializable {

    private static final long serialVersionUID = 1837484051562906817L;

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
        this.lastUpdate = Objects.requireNonNull(lastUpdate, "lastUpdate can not be null for object : " + toString());
        this.region = Objects.requireNonNull(region, "region can not be null for object : " + toString());
        this.itemId = Objects.requireNonNull(itemId, "itemId can not be null for object : " + toString());
        this.item = Objects.requireNonNull(item, "item can not be null for object : " + toString());
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

    @Override
    public String toString() {
        return "Entity{" +
                "lastUpdate=" + lastUpdate +
                ", region=" + region +
                ", itemId=" + itemId +
                ", item=" + item +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entity)) {
            return false;
        }
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(lastUpdate, entity.lastUpdate) &&
                region == entity.region &&
                Objects.equals(itemId, entity.itemId) &&
                Objects.equals(item, entity.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastUpdate, region, itemId, item);
    }
}
