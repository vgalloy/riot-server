package vgalloy.riot.server.service.api.model;

import vgalloy.riot.api.rest.constant.Region;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public class Model<DTO> {

    private final LocalDateTime lastUpdate;
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
    public Model(Region region, Long itemId, DTO item, LocalDateTime lastUpdate) {
        this.lastUpdate = Objects.requireNonNull(lastUpdate, "lastUpdate can not be null");
        this.region = Objects.requireNonNull(region, "region can not be null");
        this.itemId = Objects.requireNonNull(itemId, "itemId can not be null");
        this.item = Objects.requireNonNull(item, "item can not be null");
    }

    public LocalDateTime getLastUpdate() {
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
