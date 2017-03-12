package vgalloy.riot.server.loader.internal.loader.mapper;

import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 03/12/16.
 * Implementation of this class should be thread safe.
 *
 * @author Vincent Galloy
 */
public interface LoadingMessageMapper<T> {

    /**
     * Extract object from the {@link LoadingMessage}.
     *
     * @param loadingMessage the loading message
     * @return the object
     */
    T extract(LoadingMessage loadingMessage);

    /**
     * Wrap the value into the {@link LoadingMessage}.
     *
     * @param value the value
     * @return the loading message
     */
    LoadingMessage wrap(T value);
}
