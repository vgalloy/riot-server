package vgalloy.riot.server.webservice.internal.mapper.gameid;

import org.springframework.stereotype.Component;

import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.webservice.internal.mapper.AbstractSerializer;
import vgalloy.riot.server.webservice.internal.mapper.Mapper;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
@Component
final class GameIdConverter extends AbstractSerializer<GameId> {

    private static final long serialVersionUID = 4299902797629912725L;

    /**
     * Constructor.
     *
     * @param mapper the corresponding mapper
     */
    protected GameIdConverter(Mapper<GameId, String> mapper) {
        super(mapper);
    }
}
