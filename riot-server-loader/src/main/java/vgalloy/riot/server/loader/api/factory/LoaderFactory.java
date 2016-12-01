package vgalloy.riot.server.loader.api.factory;

import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.loader.internal.factory.BrokerFactory;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/10/16.
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
        return BrokerFactory.getSummonerLoaderClient();
    }
}
