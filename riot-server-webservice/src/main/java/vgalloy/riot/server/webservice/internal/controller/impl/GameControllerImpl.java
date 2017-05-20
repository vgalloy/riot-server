package vgalloy.riot.server.webservice.internal.controller.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.server.service.api.model.game.Game;
import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.webservice.api.controller.GameController;
import vgalloy.riot.server.webservice.internal.exception.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.exception.ResourceNotLoadedException;

/**
 * Created by Vincent Galloy on 09/07/16.
 *
 * @author Vincent Galloy
 */
@RestController
final class GameControllerImpl implements GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameControllerImpl.class);

    private final MatchDetailService matchDetailService;

    /**
     * Constructor.
     *
     * @param matchDetailService the matchDetailService
     */
    GameControllerImpl(MatchDetailService matchDetailService) {
        this.matchDetailService = Objects.requireNonNull(matchDetailService);
    }

    @Override
    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable GameId gameId) {
        LOGGER.info("[ GET ] : getGame, gameId : {}, ", gameId);

        return matchDetailService.get(gameId)
            .ifNotLoadedThrow(ResourceNotLoadedException::new)
            .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }
}
