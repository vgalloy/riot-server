package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/10/16.
 *         This class represent all information for one game.
 */
public class GameInformation implements Serializable {

    private static final long serialVersionUID = -7279832513719280220L;

    private final List<PlayerTimeline> playerTimelines;

    /**
     * Constructor.
     *
     * @param playerTimelines the player timeline
     */
    public GameInformation(List<PlayerTimeline> playerTimelines) {
        this.playerTimelines = Objects.requireNonNull(playerTimelines);
    }

    public List<PlayerTimeline> getPlayerTimelines() {
        return playerTimelines;
    }
}
