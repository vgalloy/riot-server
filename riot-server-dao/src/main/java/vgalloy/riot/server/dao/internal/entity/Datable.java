package vgalloy.riot.server.dao.internal.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/06/16.
 */
public abstract class Datable<T> { // TODO Remove

    protected final T item;
    protected final long lastUpdate;

    /**
     * Constructor.
     *
     * @param item the item
     */
    public Datable(T item) {
        this.item = Objects.requireNonNull(item, "Can not build a Datable with a null item");
        lastUpdate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

    public T getItem() {
        return item;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }
}
