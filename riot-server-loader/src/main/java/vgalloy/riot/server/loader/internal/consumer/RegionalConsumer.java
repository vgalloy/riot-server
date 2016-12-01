package vgalloy.riot.server.loader.internal.consumer;

import java.util.Objects;
import java.util.function.Consumer;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.internal.consumer.impl.JacksonMarshallerImpl;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public interface RegionalConsumer extends Consumer<LoadingMessage> {

    /**
     * Get the queue definition.
     *
     * @param region the region
     * @return the queue name
     */
    static ConsumerQueueDefinition<LoadingMessage> getQueueDefinition(Region region) {
        ConsumerQueueDefinition<LoadingMessage> queueDefinition = Factory.createQueue("LOADER_" + Objects.requireNonNull(region), LoadingMessage.class);
        queueDefinition.setMarshaller(new JacksonMarshallerImpl());
        return queueDefinition;
    }
}
