package vgalloy.riot.server.loader.internal.factory;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import vgalloy.riot.server.core.api.config.ConfigurationLoader;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.loader.api.service.exception.LoaderException;
import vgalloy.riot.server.loader.internal.factory.supplier.ConnectionFactorySupplier;
import vgalloy.riot.server.loader.internal.factory.supplier.ConsumerSupplier;
import vgalloy.riot.server.loader.internal.factory.supplier.LoaderClientSupplier;

/**
 * Created by Vincent Galloy on 11/10/16.
 *
 * @author Vincent Galloy
 */
public final class BrokerFactory {

    private static final LoaderClient LOADER_CLIENT;

    static {
        Configuration configuration;
        try {
            configuration = ConfigurationLoader.loadConfiguration();
        } catch (ConfigurationException e) {
            throw new LoaderException(e);
        }
        ConnectionFactory connectionFactory = new ConnectionFactorySupplier(configuration).get();
        LOADER_CLIENT = new LoaderClientSupplier(connectionFactory).get();
        new ConsumerSupplier(connectionFactory).get();
    }

    /**
     * Constructor.
     * To prevent instantiation
     */
    private BrokerFactory() {
        throw new AssertionError();
    }

    /**
     * Create the connection factory.
     *
     * @return the connection factory
     */
    public static LoaderClient getLoaderClient() {
        return LOADER_CLIENT;
    }
}
