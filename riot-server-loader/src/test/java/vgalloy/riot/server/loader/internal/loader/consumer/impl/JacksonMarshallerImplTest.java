package vgalloy.riot.server.loader.internal.loader.consumer.impl;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.javaoverrabbitmq.api.marshaller.RabbitMessageMarshaller;
import vgalloy.riot.server.loader.internal.loader.message.SummonerLoadingMessage;

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
        SummonerLoadingMessage summonerLoadingMessage = SummonerLoadingMessage.byId(2L);

        System.out.println(new String(rabbitMessageMarshaller.serialize(summonerLoadingMessage)));

        Assert.assertEquals(summonerLoadingMessage, rabbitMessageMarshaller.deserialize(SummonerLoadingMessage.class, rabbitMessageMarshaller.serialize(summonerLoadingMessage)));
    }
}