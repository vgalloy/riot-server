package vgalloy.riot.server.loader.api.factory;

import vgalloy.riot.server.loader.api.service.SummonerLoaderClient;
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
     * Get the instance of SummonerLoaderClient.
     *
     * @return the SummonerLoaderClient
     */
    public static SummonerLoaderClient getSummonerLoaderClient() {
        return BrokerFactory.getSummonerLoaderClient();
    }
}
