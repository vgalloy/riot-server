package vgalloy.riot.server.loader.internal.factory.supplier;

import java.util.Objects;
import java.util.function.Supplier;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public final class ConnectionFactorySupplier implements Supplier<ConnectionFactory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactorySupplier.class);

    private final Configuration configuration;

    /**
     * Constructor.
     *
     * @param configuration the configuration
     */
    public ConnectionFactorySupplier(Configuration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    @Override
    public ConnectionFactory get() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            String brokerUrl = configuration.getString("broker.url");
            int brokerPort = configuration.getInt("broker.port");
            String brokerUsername = configuration.getString("broker.username");
            String brokerPassword = configuration.getString("broker.password");

            connectionFactory.setHost(brokerUrl);
            connectionFactory.setPort(brokerPort);
            connectionFactory.setUsername(brokerUsername);
            connectionFactory.setPassword(brokerPassword);
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
        return connectionFactory;
    }
}
