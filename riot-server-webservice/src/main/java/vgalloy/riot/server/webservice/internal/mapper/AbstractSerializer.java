package vgalloy.riot.server.webservice.internal.mapper;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Vincent Galloy on 10/03/17.
 *
 * @author Vincent Galloy
 */
public abstract class AbstractSerializer<T> extends StdSerializer<T> implements Converter<String, T> {

    private static final long serialVersionUID = 3525241913559866266L;

    private final Mapper<T, String> mapper;

    /**
     * Constructor.
     *
     * @param mapper the corresponding mapper
     */
    protected AbstractSerializer(Mapper<T, String> mapper) {
        super((Class<T>) null);
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String objectAsString = mapper.map(t);
        jsonGenerator.writeString(objectAsString);
    }

    @Override
    public T convert(String s) {
        return mapper.unmap(s);
    }
}
