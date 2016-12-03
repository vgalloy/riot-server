package vgalloy.riot.server.loader.internal.factory;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.loader.api.service.exception.LoaderException;
import vgalloy.riot.server.loader.internal.factory.supplier.ConnectionFactorySupplier;
import vgalloy.riot.server.loader.internal.factory.supplier.LoaderClientSupplier;

/**
 * @author Vincent Galloy - 11/10/16
 *         Created by Vincent Galloy on 11/10/16.
 */
public final class BrokerFactory {

    private static final LoaderClient LOADER_CLIENT;

    static {
        Configuration configuration;
        try {
            configuration = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName("application.properties"))
                    .getConfiguration();
        } catch (ConfigurationException e) {
            throw new LoaderException("Unable to load configuration", e);
        }
        ConnectionFactory connectionFactory = new ConnectionFactorySupplier(configuration).get();
        LOADER_CLIENT = new LoaderClientSupplier(connectionFactory).get();
        Initializer.INSTANCE.startConsumer(connectionFactory);
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
