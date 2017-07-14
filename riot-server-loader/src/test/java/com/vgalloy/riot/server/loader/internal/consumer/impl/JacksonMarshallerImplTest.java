package com.vgalloy.riot.server.loader.internal.consumer.impl;

import org.junit.Assert;
import org.junit.Test;
import vgalloy.javaoverrabbitmq.api.marshaller.RabbitMessageMarshaller;

import com.vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageBuilder;
import com.vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 30/10/16.
 *
 * @author Vincent Galloy
 */
public final class JacksonMarshallerImplTest {

    @Test
    public void testSerializationDeserialization() {
        // GIVEN
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshallerImpl();
        Long test = 2L;

        // WHEN
        Long result = rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(test));

        // THEN
        Assert.assertEquals(test, result);
    }

    @Test
    public void testSerializationNull() {
        // GIVEN
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshallerImpl();

        // WHEN
        Long result = rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(null));

        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void testSummonerLoadingMessage() {
        // GIVEN
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshallerImpl();
        LoadingMessage loadingMessage = LoadingMessageBuilder.summonerId().wrap(2L);

        // WHEN
        LoadingMessage result = rabbitMessageMarshaller.deserialize(LoadingMessage.class, rabbitMessageMarshaller.serialize(loadingMessage));

        // THEN
        Assert.assertEquals(loadingMessage, result);
    }
}