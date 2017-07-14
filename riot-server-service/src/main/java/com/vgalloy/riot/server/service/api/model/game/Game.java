package com.vgalloy.riot.server.service.api.model.game;

import java.util.Objects;

/**
 * Created by Vincent Galloy on 07/10/16.
 * This class represent a game. The {@link GameInformation} can be null if the id have been checked and doesn't correspond to a riot game.
 *
 * @author Vincent Galloy
 */
public final class Game {

    private final GameId gameId;
    private final GameInformation gameInformation;

    /**
     * Constructor.
     *
     * @param gameId          the game id
     * @param gameInformation the game information (can be null)
     */
    public Game(GameId gameId, GameInformation gameInformation) {
        this.gameId = Objects.requireNonNull(gameId);
        this.gameInformation = gameInformation;
    }

    public GameId getGameId() {
        return gameId;
    }

    public GameInformation getGameInformation() {
        return gameInformation;
    }
}
