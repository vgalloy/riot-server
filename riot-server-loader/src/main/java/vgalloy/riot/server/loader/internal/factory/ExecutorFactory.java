package vgalloy.riot.server.loader.internal.factory;

import org.apache.commons.configuration2.Configuration;

import vgalloy.riot.api.api.factory.RiotApiFactory;
import vgalloy.riot.api.api.model.RateLimit;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.model.RiotApiKey;
import vgalloy.riot.server.loader.internal.executor.Executor;
import vgalloy.riot.server.loader.internal.executor.impl.ExecutorImpl;
import vgalloy.riot.server.loader.internal.factory.supplier.ConfigurationSupplier;

/**
 * Created by Vincent Galloy on 12/10/16.
 *
 * @author Vincent Galloy
 */
public final class ExecutorFactory {

    private static final RiotApi RIOT_API = RiotApiFactory.newRiotApi().addGlobalRateLimit(new RateLimit(5, 10 * 1000), new RateLimit(400, 10 * 60 * 1000));
    private static final Executor EXECUTOR;

    static {
        Configuration configuration = new ConfigurationSupplier("application.properties").get();
        String apiKey = configuration.getString("api_key");
        EXECUTOR = new ExecutorImpl(new RiotApiKey(apiKey));
    }

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ExecutorFactory() {
        throw new AssertionError();
    }

    /**
     * Create a riot api without valid key.
     *
     * @return the riot api
     */
    public static RiotApi getRiotApi() {
        return RIOT_API;
    }

    /**
     * Create an executor.
     *
     * @return the executor
     */
    public static Executor getExecutor() {
        return EXECUTOR;
    }
}
