package vgalloy.riot.server.loader.internal.loader.mapper.impl;

import java.util.Objects;

import vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageMapper;
import vgalloy.riot.server.loader.internal.loader.message.LoaderType;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public final class IntegerMessageMapper implements LoadingMessageMapper<Integer> {

    private final LoaderType loaderType;

    /**
     * Constructor.
     *
     * @param loaderType the loader type
     */
    public IntegerMessageMapper(LoaderType loaderType) {
        this.loaderType = Objects.requireNonNull(loaderType);
    }

    @Override
    public Integer extract(LoadingMessage loadingMessage) {
        Objects.requireNonNull(loadingMessage);
        return Integer.valueOf(loadingMessage.getValue());
    }

    @Override
    public LoadingMessage wrap(Integer value) {
        Objects.requireNonNull(value);
        return new LoadingMessage(loaderType, value.toString());
    }
}
