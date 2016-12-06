package vgalloy.riot.server.dao.api.entity;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.AbstractItemWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class Entity<DTO, ID extends ItemId> extends AbstractItemWrapper<DTO, ID> {

    private static final long serialVersionUID = 1837484051562906817L;

    private final Long lastUpdate;

    /**
     * Constructor.
     *
     * @param itemId     the item id
     * @param item       the item
     * @param lastUpdate the last update time in second (UTC)
     */
    public Entity(ID itemId, DTO item, Long lastUpdate) {
        super(itemId, item);
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
    }

    /**
     * Constructor.
     *
     * @param itemId     the item id
     * @param lastUpdate the last update time in second (UTC)
     */
    public Entity(ID itemId, Long lastUpdate) {
        super(itemId);
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public String toString() {
        return "Entity{" +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
