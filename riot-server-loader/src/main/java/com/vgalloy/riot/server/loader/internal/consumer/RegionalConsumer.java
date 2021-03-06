package com.vgalloy.riot.server.loader.internal.consumer;

import java.util.Objects;
import java.util.function.Consumer;

import vgalloy.javaoverrabbitmq.api.Factory;
import vgalloy.javaoverrabbitmq.api.queue.ConsumerQueueDefinition;
import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.loader.internal.consumer.impl.JacksonMarshallerImpl;
import com.vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 10/10/16.
 *
 * @author Vincent Galloy
 */
public interface RegionalConsumer extends Consumer<LoadingMessage> {

    /**
     * Get the queue definition.
     *
     * @param region the region
     * @return the queue name
     */
    static ConsumerQueueDefinition<LoadingMessage> getQueueDefinition(Region region) {
        Objects.requireNonNull(region);

        ConsumerQueueDefinition<LoadingMessage> queueDefinition = Factory.createQueue("LOADER_" + region, LoadingMessage.class);
        queueDefinition.setMarshaller(new JacksonMarshallerImpl());
        return queueDefinition;
    }
}
