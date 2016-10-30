package vgalloy.riot.server.loader.internal.loader.consumer;

import java.util.Objects;
import java.util.function.Consumer;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.internal.loader.consumer.impl.JacksonMarshallerImpl;
import vgalloy.riot.server.loader.internal.loader.message.SummonerLoadingMessage;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public interface RegionalSummonerLoaderConsumer extends Consumer<SummonerLoadingMessage> {

    /**
     * Get the queue definition.
     *
     * @param region the region
     * @return the queue name
     */
    static ConsumerQueueDefinition<SummonerLoadingMessage> getQueueDefinition(Region region) {
        ConsumerQueueDefinition<SummonerLoadingMessage> queueDefinition = Factory.createQueue("LOADER_" + Objects.requireNonNull(region), SummonerLoadingMessage.class);
        queueDefinition.setMarshaller(new JacksonMarshallerImpl());
        return queueDefinition;
    }
}
