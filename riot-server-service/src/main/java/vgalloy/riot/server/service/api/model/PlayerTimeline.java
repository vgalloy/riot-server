package vgalloy.riot.server.service.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/09/16.
 */
public class PlayerTimeline implements Serializable {

    private static final long serialVersionUID = -7718763464685566742L;

    private final Region region;
    private final Long playerId;
    private final List<TimedEvent<Integer>> farming = new ArrayList<>();
    private final List<TimedEvent<Position>> position = new ArrayList<>();
    private final List<TimedEvent<Integer>> gold = new ArrayList<>();
    private final List<TimedEvent<Integer>> level = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param region   the region of the player
     * @param playerId the player id
     */
    public PlayerTimeline(Region region, Long playerId) {
        this.region = Objects.requireNonNull(region);
        this.playerId = Objects.requireNonNull(playerId);
    }

    public Region getRegion() {
        return region;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public List<TimedEvent<Integer>> getFarming() {
        return farming;
    }

    public List<TimedEvent<Position>> getPosition() {
        return position;
    }

    public List<TimedEvent<Integer>> getGold() {
        return gold;
    }

    public List<TimedEvent<Integer>> getLevel() {
        return level;
    }
}
