package vgalloy.riot.server.loader.internal.factory.supplier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.model.RabbitElement;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;
import vgalloy.riot.server.loader.internal.consumer.RegionalConsumer;
import vgalloy.riot.server.loader.internal.consumer.impl.RegionalConsumerImpl;
import vgalloy.riot.server.loader.internal.factory.ExecutorFactory;
import vgalloy.riot.server.loader.internal.loader.ChampionLoader;
import vgalloy.riot.server.loader.internal.loader.ItemLoader;
import vgalloy.riot.server.loader.internal.loader.SummonerLoader;
import vgalloy.riot.server.loader.internal.loader.impl.ChampionLoaderImpl;
import vgalloy.riot.server.loader.internal.loader.impl.ItemLoaderImpl;
import vgalloy.riot.server.loader.internal.loader.impl.SummonerLoaderImpl;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 03/12/16.
 */
public final class ConsumerSupplier implements Supplier<Map<Region, RabbitElement>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerSupplier.class);

    private final ConnectionFactory connectionFactory;

    /**
     * Constructor.
     *
     * @param connectionFactory the connection factory
     */
    public ConsumerSupplier(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
    }

    @Override
    public Map<Region, RabbitElement> get() {
        Map<Region, RabbitElement> map = new HashMap<>();
        ItemLoader itemLoader = new ItemLoaderImpl(ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), MongoDaoFactory.getItemDao());
        ChampionLoader championLoader = new ChampionLoaderImpl(ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), MongoDaoFactory.getChampionDao());

        try {
            for (Region region : Region.values()) {
                ConsumerQueueDefinition<LoadingMessage> queueDefinition = RegionalConsumer.getQueueDefinition(region);
                SummonerLoader summonerLoader = new SummonerLoaderImpl(ExecutorFactory.getRiotApi(),
                        ExecutorFactory.getExecutor(),
                        MongoDaoFactory.getSummonerDao(),
                        MongoDaoFactory.getMatchDetailDao(),
                        MongoDaoFactory.getRankedStatsDao());

                Consumer<LoadingMessage> consumer = new RegionalConsumerImpl(region, summonerLoader, itemLoader, championLoader);
                RabbitElement rabbitElement = Factory.createConsumer(connectionFactory, queueDefinition, consumer);
                map.put(region, rabbitElement);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to start consumer", e);
        }
        return map;
    }
}
