package vgalloy.riot.server.service.internal.loader.consumer;

import java.util.Objects;
import java.util.function.Consumer;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.internal.loader.consumer.impl.JacksonMarshaller;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public interface RegionalSummonerLoaderConsumer extends Consumer<Long> {

    /**
     * Get the queue definition.
     *
     * @param region the region
     * @return the queue name
     */
    static ConsumerQueueDefinition<Long> getQueueDefinition(Region region) {
        ConsumerQueueDefinition<Long> queueDefinition = Factory.createQueue("LOADER_" + Objects.requireNonNull(region), Long.class);
        queueDefinition.setMarshaller(new JacksonMarshaller());
        return queueDefinition;
    }

    /**
     * Load the summoner, his ranked stats and all his recent games and save it.
     *
     * @param summonerId the summoner id
     */
    void loaderSummoner(Long summonerId);
}
