package vgalloy.riot.server.webservice.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.mapper.MatchDetailIdMapper;
import vgalloy.riot.server.service.api.model.Game;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.api.service.exception.UserException;

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
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET)
    public Game getGame(@PathVariable String gameId) {
        LOGGER.info("[ GET ] : getGame, gameId : {}, ", gameId);
        MatchDetailId matchDetailId;
        try {
            matchDetailId = MatchDetailIdMapper.map(gameId);
        } catch (IllegalArgumentException e) {
            throw new UserException(e.getMessage());
        }

        Optional<Game> optionalResult = matchDetailService.get(matchDetailId);
        if (optionalResult.isPresent()) {
            return optionalResult.get();
        }
        return null;
    }
}
