package com.vgalloy.riot.server.loader.internal.loader.mapper.impl;

import java.util.Objects;

import com.vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageMapper;
import com.vgalloy.riot.server.loader.internal.loader.message.LoaderType;
import com.vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public final class LongMessageMapper implements LoadingMessageMapper<Long> {

    private final LoaderType loaderType;

    /**
     * Constructor.
     *
     * @param loaderType the loader type
     */
    public LongMessageMapper(LoaderType loaderType) {
        this.loaderType = Objects.requireNonNull(loaderType);
    }

    @Override
    public Long extract(LoadingMessage loadingMessage) {
        Objects.requireNonNull(loadingMessage);
        return Long.valueOf(loadingMessage.getValue());
    }

    @Override
    public LoadingMessage wrap(Long value) {
        Objects.requireNonNull(value);
        return new LoadingMessage(loaderType, value.toString());
    }
}
