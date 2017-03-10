package vgalloy.riot.server.webservice.internal.mapper.gameid;

import org.springframework.stereotype.Component;

import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.webservice.internal.mapper.AbstractSerializer;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
@Component
public final class GameIdConverter extends AbstractSerializer<GameId> {

    private static final long serialVersionUID = 4299902797629912725L;

    @Override
    protected GameId map(String string) {
        return GameIdMapper.map(string);
    }

    @Override
    protected String unmap(GameId gameId) {
        return GameIdMapper.map(gameId);
    }
}
