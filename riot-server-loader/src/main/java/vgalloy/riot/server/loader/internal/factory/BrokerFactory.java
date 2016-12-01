package vgalloy.riot.server.loader.internal.factory;

import java.util.function.Consumer;

import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.loader.internal.client.impl.LoaderClientImpl;
import vgalloy.riot.server.loader.internal.consumer.RegionalConsumer;
import vgalloy.riot.server.loader.internal.consumer.impl.RegionalConsumerImpl;
import vgalloy.riot.server.loader.internal.loader.ItemLoader;
import vgalloy.riot.server.loader.internal.loader.SummonerLoader;
import vgalloy.riot.server.loader.internal.loader.impl.ItemLoaderImpl;
import vgalloy.riot.server.loader.internal.loader.impl.SummonerLoaderImpl;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

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
    private static final LoaderClient SUMMONER_LOADER_CLIENT;

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

            SUMMONER_LOADER_CLIENT = new LoaderClientImpl(CONNECTION_FACTORY);

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
    public static LoaderClient getSummonerLoaderClient() {
        return SUMMONER_LOADER_CLIENT;
    }

    /**
     * Start the consumer.
     */
    private static void startConsumer() { // TODO faire un mode dégradé ?
        for (Region region : Region.values()) {
            ConsumerQueueDefinition<LoadingMessage> queueDefinition = RegionalConsumer.getQueueDefinition(region);
            SummonerLoader summonerLoader = new SummonerLoaderImpl(ExecutorFactory.getRiotApi(),
                    ExecutorFactory.getExecutor(), MongoDaoFactory.getSummonerDao(), MongoDaoFactory.getMatchDetailDao(), MongoDaoFactory.getRankedStatsDao());
            ItemLoader itemLoader = new ItemLoaderImpl(ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), MongoDaoFactory.getItemDao());

            Consumer<LoadingMessage> consumer = new RegionalConsumerImpl(region, summonerLoader, itemLoader);
            Factory.createConsumer(CONNECTION_FACTORY, queueDefinition, consumer);
        }
    }
}
