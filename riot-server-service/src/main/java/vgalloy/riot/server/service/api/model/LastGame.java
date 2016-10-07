package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.util.Objects;

import vgalloy.riot.api.api.constant.QueueType;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/08/16.
 */
public class LastGame implements Serializable {

    private static final long serialVersionUID = 742436102510397197L;

    private final String gameId;
    private final int championId;
    private final long kill;
    private final long death;
    private final long assist;
    private final boolean winner;
    private final long matchCreation;
    private final QueueType queueType;

    /**
     * Constructor.
     *
     * @param gameId        the game id
     * @param championId    the champion id
     * @param kill          the number of kill
     * @param death         the number of death
     * @param assist        the number of assist
     * @param winner        is the game a win
     * @param matchCreation the match creation in millis
     * @param queueType     the queue type
     */
    public LastGame(String gameId, int championId, long kill, long death, long assist, boolean winner, long matchCreation, QueueType queueType) {
        this.gameId = gameId;
        this.championId = championId;
        this.kill = kill;
        this.death = death;
        this.assist = assist;
        this.winner = winner;
        this.matchCreation = matchCreation;
        this.queueType = Objects.requireNonNull(queueType);
    }

    public String getGameId() {
        return gameId;
    }

    public int getChampionId() {
        return championId;
    }

    public long getKill() {
        return kill;
    }

    public long getDeath() {
        return death;
    }

    public long getAssist() {
        return assist;
    }

    public boolean isWinner() {
        return winner;
    }

    public long getMatchCreation() {
        return matchCreation;
    }

    public QueueType getQueueType() {
        return queueType;
    }
}
