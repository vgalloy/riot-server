package vgalloy.riot.server.service.api.model.game;

import java.util.List;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/10/16.
 *         This class represent all information for one game.
 */
public class GameInformation {

    private final List<PlayerTimeline> playerTimelines;
    private final List<SummonerInformation> summonerInformation;

    /**
     * Constructor.
     *
     * @param playerTimelines     the player timeline
     * @param summonerInformation the player information
     */
    public GameInformation(List<PlayerTimeline> playerTimelines, List<SummonerInformation> summonerInformation) {
        this.playerTimelines = Objects.requireNonNull(playerTimelines);
        this.summonerInformation = Objects.requireNonNull(summonerInformation);
    }

    public List<PlayerTimeline> getPlayerTimelines() {
        return playerTimelines;
    }

    public List<SummonerInformation> getSummonerInformation() {
        return summonerInformation;
    }
}
