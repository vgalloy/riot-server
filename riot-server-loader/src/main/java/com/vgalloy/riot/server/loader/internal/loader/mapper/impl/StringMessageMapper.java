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
public final class StringMessageMapper implements LoadingMessageMapper<String> {

    private final LoaderType loaderType;

    /**
     * Constructor.
     *
     * @param loaderType the loader type
     */
    public StringMessageMapper(LoaderType loaderType) {
        this.loaderType = Objects.requireNonNull(loaderType);
    }

    @Override
    public String extract(LoadingMessage loadingMessage) {
        Objects.requireNonNull(loadingMessage);
        return loadingMessage.getValue();
    }

    @Override
    public LoadingMessage wrap(String value) {
        Objects.requireNonNull(value);
        return new LoadingMessage(loaderType, value);
    }
}
