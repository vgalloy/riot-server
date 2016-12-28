package vgalloy.riot.server.dao.api.entity;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.AbstractDpoWrapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class Entity<DTO, ID extends DpoId> extends AbstractDpoWrapper<DTO, ID> {

    private static final long serialVersionUID = 1837484051562906817L;

    private final Long lastUpdate;

    /**
     * Constructor.
     *
     * @param itemId     the item id
     * @param item       the item (can be null)
     * @param lastUpdate the last update time in second (UTC)
     */
    public Entity(ID itemId, DTO item, Long lastUpdate) {
        super(itemId, item);
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
