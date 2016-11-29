package vgalloy.riot.server.dao.api.entity;

import java.io.Serializable;
import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.wrapper.AbstractItemWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class Entity<WRAPPER extends AbstractItemWrapper<?, ?>> implements Serializable {

    private static final long serialVersionUID = 1837484051562906817L;

    private final WRAPPER itemWrapper;
    private final Long lastUpdate;

    /**
     * Constructor.
     *
     * @param itemWrapper the item wrapper
     * @param lastUpdate  the last update time in second (UTC)
     */
    public Entity(WRAPPER itemWrapper, Long lastUpdate) {
        this.itemWrapper = Objects.requireNonNull(itemWrapper);
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
    }

    public WRAPPER getItemWrapper() {
        return itemWrapper;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "itemWrapper=" + itemWrapper +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
