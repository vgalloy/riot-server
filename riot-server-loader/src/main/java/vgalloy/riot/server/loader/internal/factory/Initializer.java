package vgalloy.riot.server.loader.internal.factory;

import java.util.function.Consumer;

import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;
import vgalloy.riot.server.loader.internal.consumer.RegionalConsumer;
import vgalloy.riot.server.loader.internal.consumer.impl.RegionalConsumerImpl;
import vgalloy.riot.server.loader.internal.loader.ItemLoader;
import vgalloy.riot.server.loader.internal.loader.SummonerLoader;
import vgalloy.riot.server.loader.internal.loader.impl.ItemLoaderImpl;
import vgalloy.riot.server.loader.internal.loader.impl.SummonerLoaderImpl;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 03/12/16.
 */
public enum Initializer {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(Initializer.class);

    /**
     * Start the consumer.
     *
     * @param connectionFactory the connectionFactory
     */
    public void startConsumer(ConnectionFactory connectionFactory) {
        try {
            for (Region region : Region.values()) {
                ConsumerQueueDefinition<LoadingMessage> queueDefinition = RegionalConsumer.getQueueDefinition(region);
                SummonerLoader summonerLoader = new SummonerLoaderImpl(ExecutorFactory.getRiotApi(),
                        ExecutorFactory.getExecutor(), MongoDaoFactory.getSummonerDao(), MongoDaoFactory.getMatchDetailDao(), MongoDaoFactory.getRankedStatsDao());
                ItemLoader itemLoader = new ItemLoaderImpl(ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), MongoDaoFactory.getItemDao());

                Consumer<LoadingMessage> consumer = new RegionalConsumerImpl(region, summonerLoader, itemLoader);
                Factory.createConsumer(connectionFactory, queueDefinition, consumer);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to start consumer", e);
        }
    }
}
