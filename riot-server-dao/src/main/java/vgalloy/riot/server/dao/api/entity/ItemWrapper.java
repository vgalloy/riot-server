package vgalloy.riot.server.dao.api.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 05/10/16
 *         Created by Vincent Galloy on 05/10/16.
 */
public class ItemWrapper<DTO> implements Serializable {

    private static final long serialVersionUID = 5745585472522922118L;

    private final Region region;
    private final Long itemId;
    private final DTO item;

    /**
     * @param region the region
     * @param itemId the item id
     * @param item   the item (can be null)
     */
    public ItemWrapper(Region region, Long itemId, DTO item) {
        this.region = Objects.requireNonNull(region);
        this.itemId = Objects.requireNonNull(itemId);
        this.item = Objects.requireNonNull(item);
    }

    /**
     * @param region the region
     * @param itemId the item id
     */
    public ItemWrapper(Region region, Long itemId) {
        this.region = Objects.requireNonNull(region);
        this.itemId = Objects.requireNonNull(itemId);
        item = null;
    }

    public Region getRegion() {
        return region;
    }

    public Long getItemId() {
        return itemId;
    }

    public Optional<DTO> getItem() {
        return Optional.ofNullable(item);
    }
}
