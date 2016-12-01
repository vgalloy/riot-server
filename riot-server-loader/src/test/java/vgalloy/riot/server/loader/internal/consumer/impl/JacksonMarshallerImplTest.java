package vgalloy.riot.server.loader.internal.consumer.impl;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.javaoverrabbitmq.api.marshaller.RabbitMessageMarshaller;
import vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageBuilder;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/10/16.
 */
public class JacksonMarshallerImplTest {

    @Test
    public void testSerializationDeserialization() {
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshallerImpl();
        Long test = 2L;

        Assert.assertEquals(test, rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(test)));
        Assert.assertNotEquals(new Long(3), rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(test)));
    }

    @Test
    public void testSerializationNull() {
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshallerImpl();

        Assert.assertNull(null, rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(null)));
    }

    @Test
    public void testSummonerLoadingMessage() {
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshallerImpl();
        LoadingMessage loadingMessage = LoadingMessageBuilder.summonerId().wrap(2L);

        Assert.assertEquals(loadingMessage, rabbitMessageMarshaller.deserialize(LoadingMessage.class, rabbitMessageMarshaller.serialize(loadingMessage)));
    }
}