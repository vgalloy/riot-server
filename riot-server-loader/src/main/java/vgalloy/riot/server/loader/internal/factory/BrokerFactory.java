package vgalloy.riot.server.loader.internal.factory;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.configuration2.Configuration;

import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.loader.internal.factory.supplier.ConfigurationSupplier;
import vgalloy.riot.server.loader.internal.factory.supplier.ConnectionFactorySupplier;
import vgalloy.riot.server.loader.internal.factory.supplier.ConsumerSupplier;
import vgalloy.riot.server.loader.internal.factory.supplier.LoaderClientSupplier;

/**
 * @author Vincent Galloy - 11/10/16
 *         Created by Vincent Galloy on 11/10/16.
 */
public final class BrokerFactory {

    private static final LoaderClient LOADER_CLIENT;

    static {
        Configuration configuration = new ConfigurationSupplier("application.properties").get();
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
