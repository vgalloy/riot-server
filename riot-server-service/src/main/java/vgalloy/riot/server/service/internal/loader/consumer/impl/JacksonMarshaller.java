package vgalloy.riot.server.service.internal.loader.consumer.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vgalloy.javaoverrabbitmq.api.marshaller.RabbitMessageMarshaller;

/**
 * @author Vincent Galloy - 13/10/16
 *         Created by Vincent Galloy on 13/10/16.
 */
public class JacksonMarshaller implements RabbitMessageMarshaller {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <M> byte[] serialize(M m) {
        try {
            return OBJECT_MAPPER.writeValueAsString(m).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize : " + m, e);
        }
    }

    @Override
    public <M> M deserialize(Class<M> aClass, byte... bytes) {
        try {
            return OBJECT_MAPPER.readValue(bytes, aClass);
        } catch (IOException e) {
            throw new RuntimeException("Unable to deserialize : " + bytes + " as instance of " + aClass, e);
        }
    }
}
