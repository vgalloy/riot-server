package vgalloy.riot.server.service.api.model.game;

import java.util.Objects;

/**
 * Created by Vincent Galloy on 08/09/16.
 *
 * @author Vincent Galloy
 */
public final class TimedEvent<T> {

    private final long timestamp;
    private final T value;

    /**
     * Constructor.
     *
     * @param timestamp the time after the beginning of the game in
     * @param value     the value wrapped
     */
    public TimedEvent(long timestamp, T value) {
        this.timestamp = timestamp;
        this.value = Objects.requireNonNull(value);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public T getValue() {
        return value;
    }
}
