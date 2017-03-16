package vgalloy.riot.server.webservice.internal.mapper;

import java.io.IOException;

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

    /**
     * Constructor.
     */
    protected AbstractSerializer() {
        this(null);
    }

    /**
     * The constructor.
     *
     * @param t the class
     */
    private AbstractSerializer(Class<T> t) {
        super(t);
    }

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(unmap(t));
    }

    @Override
    public T convert(String s) {
        return map(s);
    }

    /**
     * Convert a String into an Object of type T.
     *
     * @param string the string
     * @return the object
     */
    protected abstract T map(String string);

    /**
     * Convert an object into String.
     *
     * @param t the object
     * @return the object as a String
     */
    protected abstract String unmap(T t);
}
