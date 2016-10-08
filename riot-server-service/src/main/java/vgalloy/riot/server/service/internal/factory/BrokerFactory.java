package vgalloy.riot.server.service.internal.factory;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.function.Consumer;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;
import vgalloy.riot.server.service.internal.loader.client.SummonerLoaderClient;
import vgalloy.riot.server.service.internal.loader.client.SummonerLoaderClientImpl;
import vgalloy.riot.server.service.internal.loader.consumer.impl.RegionalSummonerLoaderConsumer;
import vgalloy.riot.server.service.internal.loader.consumer.RegionalSummonerLoaderConsumerImpl;

/**
 * @author Vincent Galloy - 11/10/16
 *         Created by Vincent Galloy on 11/10/16.
 */
public final class BrokerFactory {

    private static final String BROKER_URL;
    private static final int BROKER_PORT;
    private static final String BROKER_USERNAME;
    private static final String BROKER_PASSWORD;
    private static final ConnectionFactory CONNECTION_FACTORY;
    private static final SummonerLoaderClient SUMMONER_LOADER_CLIENT;

    static {
        try {
            Configuration configuration = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName("application.properties"))
                    .getConfiguration();
            BROKER_URL = (String) configuration.getProperty("broker.url");
            BROKER_PORT = Integer.valueOf((String) configuration.getProperty("broker.port"));
            BROKER_USERNAME = (String) configuration.getProperty("broker.username");
            BROKER_PASSWORD = (String) configuration.getProperty("broker.password");

            CONNECTION_FACTORY = new ConnectionFactory();
            CONNECTION_FACTORY.setHost(BROKER_URL);
            CONNECTION_FACTORY.setPort(BROKER_PORT);
            CONNECTION_FACTORY.setUsername(BROKER_USERNAME);
            CONNECTION_FACTORY.setPassword(BROKER_PASSWORD);

            SUMMONER_LOADER_CLIENT = new SummonerLoaderClientImpl(CONNECTION_FACTORY);

            startConsumer();
        } catch (ConfigurationException e) {
            throw new MongoDaoException("Unable to load configuration", e);
        }
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
    public static ConnectionFactory getConnectionFactory() {
        return CONNECTION_FACTORY;
    }

    /**
     * Create the connection factory.
     *
     * @return the connection factory
     */
    public static SummonerLoaderClient getSummonerLoaderClient() {
        return SUMMONER_LOADER_CLIENT;
    }

    /**
     * Start the consumer.
     */
    private static void startConsumer() {
        for (Region region : Region.values()) {
            ConsumerQueueDefinition<Long> queueDefinition = Factory.createQueue(RegionalSummonerLoaderConsumer.getQueueName(region), Long.class);
            Consumer<Long> consumer = new RegionalSummonerLoaderConsumerImpl(region, ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), MongoDaoFactory.getSummonerDao(), MongoDaoFactory.getMatchDetailDao(), MongoDaoFactory.getRankedStatsDao());
            Factory.createConsumer(CONNECTION_FACTORY, queueDefinition, consumer);
        }
    }
}
