package vgalloy.riot.server.service.internal.factory;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import vgalloy.riot.api.api.factory.RiotApiFactory;
import vgalloy.riot.api.api.model.RateLimit;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.model.RiotApiKey;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.executor.impl.ExecutorImpl;

/**
 * @author Vincent Galloy - 12/10/16
 *         Created by Vincent Galloy on 12/10/16.
 */
public final class ExecutorFactory {

    private static final RiotApi RIOT_API = RiotApiFactory.newRiotApi().addGlobalRateLimit(new RateLimit(5, 10 * 1000), new RateLimit(400, 10 * 60 * 1000));
    private static final Executor EXECUTOR;

    static {
        try {
            Configuration configuration = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName("application.properties"))
                    .getConfiguration();
            String apiKey = (String) configuration.getProperty("api_key");
            EXECUTOR = new ExecutorImpl(new RiotApiKey(apiKey));
        } catch (ConfigurationException e) {
            throw new MongoDaoException("Unable to load configuration", e);
        }
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
