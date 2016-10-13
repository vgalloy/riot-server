package vgalloy.riot.server.service.internal.loader.consumer.impl;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.javaoverrabbitmq.api.marshaller.RabbitMessageMarshaller;

/**
 * @author Vincent Galloy - 13/10/16
 *         Created by Vincent Galloy on 13/10/16.
 */
public class JacksonMarshallerTest {

    @Test
    public void testSerializationDeserialization() {
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshaller();
        Long test = 2L;

        Assert.assertEquals(test, rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(test)));
        Assert.assertNotEquals(new Long(3), rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(test)));
    }

    @Test
    public void testSerializationNull() {
        RabbitMessageMarshaller rabbitMessageMarshaller = new JacksonMarshaller();

        Assert.assertNull(null, rabbitMessageMarshaller.deserialize(Long.class, rabbitMessageMarshaller.serialize(null)));
    }
}