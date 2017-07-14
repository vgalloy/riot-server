package com.vgalloy.riot.server.webservice.api.controller;

import com.vgalloy.riot.server.service.api.model.game.Game;
import com.vgalloy.riot.server.service.api.model.game.GameId;

/**
 * Created by Vincent Galloy on 09/07/16.
 *
 * @author Vincent Galloy
 */
public interface GameController {

    /**
     * Get the players information during the game.
     *
     * @param gameId the game id
     * @return the players information as a list
     */
    Game getGame(GameId gameId);
}
