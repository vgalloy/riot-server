package vgalloy.riot.server.service.internal.loader.client.impl;

import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.internal.loader.client.SummonerLoaderClient;
import vgalloy.riot.server.service.internal.loader.consumer.RegionalSummonerLoaderConsumer;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public class SummonerLoaderClientImpl implements SummonerLoaderClient {

    private final Map<Region, Consumer<Long>> map;

    /**
     * Constructor.
     *
     * @param connectionFactory the getConnectionFactory
     */
    public SummonerLoaderClientImpl(ConnectionFactory connectionFactory) {
        map = new HashMap<>();
        for (Region region : Region.values()) {
            ConsumerQueueDefinition<Long> queueDefinition = RegionalSummonerLoaderConsumer.getQueueDefinition(region);
            map.put(region, Factory.createClient(connectionFactory, queueDefinition));
        }
    }

    @Override
    public void loaderSummoner(Region region, Long summonerId) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(summonerId);
        map.get(region).accept(summonerId);
    }
}
