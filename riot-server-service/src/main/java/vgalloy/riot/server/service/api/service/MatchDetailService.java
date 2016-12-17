package vgalloy.riot.server.service.api.service;

import java.util.Optional;

import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.service.api.model.Game;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/06/16.
 */
public interface MatchDetailService {

    /**
     * Get the game.
     *
     * @param matchDetailId the game information key
     * @return the game information
     */
    Optional<Game> get(MatchDetailId matchDetailId);
}
