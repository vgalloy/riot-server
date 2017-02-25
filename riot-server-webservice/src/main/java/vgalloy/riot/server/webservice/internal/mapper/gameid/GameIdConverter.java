package vgalloy.riot.server.webservice.internal.mapper.gameid;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import vgalloy.riot.server.service.api.model.game.GameId;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/12/16.
 */
@Component
public final class GameIdConverter extends StdSerializer<GameId> implements Converter<String, GameId> {

    private static final long serialVersionUID = 4299902797629912725L;

    /**
     * Constructor.
     */
    public GameIdConverter() {
        this(null);
    }

    /**
     * The constructor.
     *
     * @param t the class
     */
    private GameIdConverter(Class<GameId> t) {
        super(t);
    }

    @Override
    public void serialize(GameId gameId, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
        jsonGenerator.writeString(GameIdMapper.map(gameId));
    }

    @Override
    public GameId convert(String source) {
        return GameIdMapper.map(source);
    }
}
