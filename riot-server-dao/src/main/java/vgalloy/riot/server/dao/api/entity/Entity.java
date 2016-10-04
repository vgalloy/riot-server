package vgalloy.riot.server.dao.api.entity;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class Entity<DTO> extends ItemWrapper<DTO> {

    private static final long serialVersionUID = 1837484051562906817L;

    private final Long lastUpdate;

    /**
     * Constructor.
     *
     * @param region     the region
     * @param itemId     the item id
     * @param item       the item
     * @param lastUpdate the last update
     */
    public Entity(Region region, Long itemId, DTO item, Long lastUpdate) {
        super(region, itemId, item);
        this.lastUpdate = Objects.requireNonNull(lastUpdate, "lastUpdate can not be null for object : " + toString());
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }
}
