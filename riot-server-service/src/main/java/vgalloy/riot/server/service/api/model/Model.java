package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public class Model<DTO> implements Serializable {

    private static final long serialVersionUID = -6093649270391641193L;

    private final LocalDateTime lastUpdate;
    private final Region region;
    private final Long itemId;
    private DTO item;

    /**
     * Constructor.
     *
     * @param region     the region
     * @param itemId     the item id
     * @param lastUpdate the last update
     */
    public Model(Region region, Long itemId, LocalDateTime lastUpdate) {
        this.lastUpdate = Objects.requireNonNull(lastUpdate, "lastUpdate can not be null");
        this.region = Objects.requireNonNull(region, "region can not be null");
        this.itemId = Objects.requireNonNull(itemId, "ItemId can not be null");
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

    public void setItem(DTO item) {
        this.item = item;
    }
}
