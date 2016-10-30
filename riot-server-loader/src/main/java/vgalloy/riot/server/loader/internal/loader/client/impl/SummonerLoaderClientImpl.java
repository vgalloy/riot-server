package vgalloy.riot.server.loader.internal.loader.client.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.rabbitmq.client.ConnectionFactory;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.api.service.SummonerLoaderClient;
import vgalloy.riot.server.loader.internal.loader.consumer.RegionalSummonerLoaderConsumer;
import vgalloy.riot.server.loader.internal.loader.message.SummonerLoadingMessage;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public class SummonerLoaderClientImpl implements SummonerLoaderClient {

    private final Map<Region, Consumer<SummonerLoadingMessage>> map;

    /**
     * Constructor.
     *
     * @param connectionFactory the getConnectionFactory
     */
    public SummonerLoaderClientImpl(ConnectionFactory connectionFactory) {
        map = new HashMap<>();
        for (Region region : Region.values()) {
            ConsumerQueueDefinition<SummonerLoadingMessage> queueDefinition = RegionalSummonerLoaderConsumer.getQueueDefinition(region);
            map.put(region, Factory.createClient(connectionFactory, queueDefinition));
        }
    }

    @Override
    public void loaderSummoner(Region region, Long summonerId) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(summonerId);
        map.get(region).accept(SummonerLoadingMessage.byId(summonerId));
    }

    @Override
    public void loaderSummoner(Region region, String summonerName) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(summonerName);
        map.get(region).accept(SummonerLoadingMessage.byName(summonerName));
    }
}
