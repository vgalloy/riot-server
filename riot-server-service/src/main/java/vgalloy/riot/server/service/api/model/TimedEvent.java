package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/09/16.
 */
public class TimedEvent<T> implements Serializable {

    private static final long serialVersionUID = 3797859626501231651L;

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
