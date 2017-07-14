package com.vgalloy.riot.server.loader.api.factory;

import com.vgalloy.riot.server.loader.api.service.LoaderClient;
import com.vgalloy.riot.server.loader.internal.factory.BrokerFactory;

/**
 * Created by Vincent Galloy on 30/10/16.
 *
 * @author Vincent Galloy
 */
public final class LoaderFactory {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private LoaderFactory() {
        throw new AssertionError();
    }

    /**
     * Get the instance of LoaderClient.
     *
     * @return the LoaderClient
     */
    public static LoaderClient getLoaderClient() {
        return BrokerFactory.getLoaderClient();
    }
}
