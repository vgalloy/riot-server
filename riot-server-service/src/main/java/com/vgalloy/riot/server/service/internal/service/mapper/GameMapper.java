package com.vgalloy.riot.server.service.internal.service.mapper;

import java.util.Objects;

import vgalloy.riot.api.api.dto.mach.MatchDetail;

import com.vgalloy.riot.server.service.api.model.game.Game;
import com.vgalloy.riot.server.service.api.model.game.GameId;
import com.vgalloy.riot.server.service.api.model.game.GameInformation;

/**
 * Created by Vincent Galloy on 07/10/16.
 *
 * @author Vincent Galloy
 */
public final class GameMapper {

    /**
     * Constructor.
     * <p>
     * To prevent instantiation
     */
    private GameMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a matchDetail into a Game.
     *
     * @param matchDetail the match detail
     * @return the Game
     */
    public static Game map(MatchDetail matchDetail) {
        Objects.requireNonNull(matchDetail);
        GameInformation gameInformation = GameInformationMapper.map(matchDetail);
        GameId gameId = GameIdMapper.map(matchDetail);
        return new Game(gameId, gameInformation);
    }
}
