package vgalloy.riot.server.webservice.internal.mapper.summonerid;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/12/16.
 */
@Component
public class SummonerIdConverter extends StdSerializer<SummonerId> implements Converter<String, SummonerId> {

    private static final long serialVersionUID = 4299902797629912725L;

    /**
     * Constructor.
     */
    public SummonerIdConverter() {
        this(null);
    }

    /**
     * The constructor.
     *
     * @param t the class
     */
    private SummonerIdConverter(Class<SummonerId> t) {
        super(t);
    }

    @Override
    public void serialize(SummonerId summonerId, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
        jsonGenerator.writeString(SummonerIdMapper.map(summonerId));
    }

    @Override
    public SummonerId convert(String source) {
        return SummonerIdMapper.map(source);
    }
}
