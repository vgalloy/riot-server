package vgalloy.riot.server.loader.internal.client.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.rabbitmq.client.ConnectionFactory;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.model.RabbitClientConsumer;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.loader.internal.consumer.RegionalConsumer;
import vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageBuilder;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 10/10/16.
 *
 * @author Vincent Galloy
 */
public final class LoaderClientImpl implements LoaderClient {

    private final Map<Region, RabbitClientConsumer<LoadingMessage>> map = new HashMap<>();

    /**
     * Constructor.
     *
     * @param connectionFactory the getConnectionFactory
     */
    public LoaderClientImpl(ConnectionFactory connectionFactory) {
        Objects.requireNonNull(connectionFactory);
        for (Region region : Region.values()) {
            ConsumerQueueDefinition<LoadingMessage> queueDefinition = RegionalConsumer.getQueueDefinition(region);
            map.put(region, Factory.createClient(connectionFactory, queueDefinition));
        }
    }

    @Override
    public int getItemInQueue(Region region) {
        Objects.requireNonNull(region);
        return map.get(region).getMessageCount();
    }

    @Override
    public void loadAsyncSummonerById(Region region, Long summonerId) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(summonerId);
        map.get(region).accept(LoadingMessageBuilder.summonerId().wrap(summonerId));
    }

    @Override
    public void loadAsyncSummonerByName(Region region, String summonerName) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(summonerName);
        map.get(region).accept(LoadingMessageBuilder.summonerName().wrap(summonerName));
    }

    @Override
    public void loadChampionById(Region region, Long championId) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(championId);
        map.get(region).accept(LoadingMessageBuilder.championId().wrap(championId));
    }

    @Override
    public void loadAsyncItemById(Region region, Integer itemId) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(itemId);
        map.get(region).accept(LoadingMessageBuilder.itemId().wrap(itemId));
    }
}
