package com.vgalloy.riot.server.service.api.service;

import com.vgalloy.riot.server.service.api.model.game.Game;
import com.vgalloy.riot.server.service.api.model.game.GameId;
import com.vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;

/**
 * Created by Vincent Galloy on 23/06/16.
 *
 * @author Vincent Galloy
 */
public interface MatchDetailService {

    /**
     * Get the game.
     *
     * @param gameId the game id
     * @return the game information
     */
    ResourceWrapper<Game> get(GameId gameId);
}
