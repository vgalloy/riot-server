package vgalloy.riot.server.loader.internal.loader.mapper.impl;

import java.util.Objects;

import vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageMapper;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public class StringMessageMapper implements LoadingMessageMapper<String> {

    private final LoadingMessage.LoaderType loaderType;

    /**
     * Constructor.
     *
     * @param loaderType the loader type
     */
    public StringMessageMapper(LoadingMessage.LoaderType loaderType) {
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
