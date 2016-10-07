package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 *         This class represente a game. The game can be null if the id have been checked and doesn't correspond to a
 *         riot game.
 */
public class Game implements Serializable {

    private static final long serialVersionUID = 5026355985228745470L;

    private final String gameId;
    private final Long lastUpdate;
    private final GameInformation gameInformation;

    /**
     * Constructor.
     * Using this constructor mean database already check the game with the given id doesn't exist.
     *
     * @param gameId     the game id
     * @param lastUpdate the last update
     */
    public Game(String gameId, Long lastUpdate) {
        this.gameId = gameId;
        this.lastUpdate = lastUpdate;
        this.gameInformation = null;
    }

    /**
     * Constructor.
     *
     * @param gameId          the game id
     * @param lastUpdate      the last update
     * @param gameInformation the game information
     */
    public Game(String gameId, Long lastUpdate, GameInformation gameInformation) {
        this.gameId = Objects.requireNonNull(gameId);
        this.lastUpdate = Objects.requireNonNull(lastUpdate);
        this.gameInformation = Objects.requireNonNull(gameInformation);
    }

    public String getGameId() {
        return gameId;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public GameInformation getPlayerTimelines() {
        return gameInformation;
    }
}
