package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/08/16.
 */
public class LastGame implements Serializable {

    private static final long serialVersionUID = 742436102510397197L;

    private final Region region;
    private final long gameId;
    private final int championId;
    private final long kill;
    private final long death;
    private final long assist;
    private final boolean winner;
    private final long matchCreation;

    /**
     * Constructor.
     *
     * @param region        the region
     * @param gameId        the game id
     * @param championId    the champion id
     * @param kill          the number of kill
     * @param death         the number of death
     * @param assist        the number of assist
     * @param winner        is the game a win
     * @param matchCreation the match creation
     */
    public LastGame(Region region, long gameId, int championId, long kill, long death, long assist, boolean winner, long matchCreation) {
        this.region = Objects.requireNonNull(region);
        this.gameId = gameId;
        this.championId = championId;
        this.kill = kill;
        this.death = death;
        this.assist = assist;
        this.winner = winner;
        this.matchCreation = matchCreation;
    }

    public Region getRegion() {
        return region;
    }

    public long getGameId() {
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
}
