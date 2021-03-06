package com.vgalloy.riot.server.loader.internal.factory.supplier;

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

import com.vgalloy.riot.server.dao.api.factory.DaoFactory;
import com.vgalloy.riot.server.loader.internal.consumer.RegionalConsumer;
import com.vgalloy.riot.server.loader.internal.consumer.impl.RegionalConsumerImpl;
import com.vgalloy.riot.server.loader.internal.factory.ExecutorFactory;
import com.vgalloy.riot.server.loader.internal.loader.ChampionLoader;
import com.vgalloy.riot.server.loader.internal.loader.ItemLoader;
import com.vgalloy.riot.server.loader.internal.loader.SummonerLoader;
import com.vgalloy.riot.server.loader.internal.loader.impl.ChampionLoaderImpl;
import com.vgalloy.riot.server.loader.internal.loader.impl.ItemLoaderImpl;
import com.vgalloy.riot.server.loader.internal.loader.impl.SummonerLoaderImpl;
import com.vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
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
        ItemLoader itemLoader = new ItemLoaderImpl(ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), DaoFactory.getItemDao());
        ChampionLoader championLoader = new ChampionLoaderImpl(ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), DaoFactory.getChampionDao());

        try {
            for (Region region : Region.values()) {
                ConsumerQueueDefinition<LoadingMessage> queueDefinition = RegionalConsumer.getQueueDefinition(region);
                SummonerLoader summonerLoader = new SummonerLoaderImpl(ExecutorFactory.getRiotApi(),
                    ExecutorFactory.getExecutor(),
                    DaoFactory.getSummonerDao(),
                    DaoFactory.getMatchDetailDao(),
                    DaoFactory.getRankedStatsDao());

                Consumer<LoadingMessage> consumer = new RegionalConsumerImpl(region, summonerLoader, itemLoader, championLoader);
                RabbitElement rabbitElement = Factory.createConsumer(connectionFactory, queueDefinition, consumer);
                map.put(region, rabbitElement);
            }
        } catch (Exception e) {
            LOGGER.warn("Unable to start consumer", e.getMessage());
        }
        return map;
    }
}
