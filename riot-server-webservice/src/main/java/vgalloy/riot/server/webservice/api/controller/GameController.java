package vgalloy.riot.server.webservice.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.server.service.api.model.game.Game;
import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.webservice.internal.model.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.model.ResourceNotLoadedException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
@RestController
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private MatchDetailService matchDetailService;

    /**
     * Get the players information during the game.
     *
     * @param gameId the game id
     * @return the players information as a list
     */
    @RequestMapping(value = "/games/{gameId}", method = RequestMethod.GET)
    public Game getGame(@PathVariable GameId gameId) {
        LOGGER.info("[ GET ] : getGame, gameId : {}, ", gameId);

        return matchDetailService.get(gameId)
                .ifNotLoadedThrow(ResourceNotLoadedException::new)
                .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }
}
