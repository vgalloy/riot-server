package vgalloy.riot.server.loader.internal.consumer.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vgalloy.javaoverrabbitmq.api.marshaller.RabbitMessageMarshaller;
import vgalloy.riot.server.loader.api.service.exception.LoaderException;

/**
 * Created by Vincent Galloy on 13/10/16.
 *
 * @author Vincent Galloy
 */
public class JacksonMarshallerImpl implements RabbitMessageMarshaller {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <M> byte[] serialize(M m) {
        try {
            return OBJECT_MAPPER.writeValueAsString(m).getBytes();
        } catch (JsonProcessingException e) {
            throw new LoaderException("Unable to serialize : " + m, e);
        }
    }

    @Override
    public <M> M deserialize(Class<M> aClass, byte... bytes) {
        try {
            return OBJECT_MAPPER.readValue(bytes, aClass);
        } catch (IOException e) {
            throw new LoaderException("Unable to deserialize : " + bytes + " as instance of " + aClass, e);
        }
    }
}
