package vgalloy.riot.server.service.api.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/09/16.
 */
public class PlayerTimeline {

    private final SummonerId summonerId;
    private final List<TimedEvent<Integer>> farming = new ArrayList<>();
    private final List<TimedEvent<Position>> position = new ArrayList<>();
    private final List<TimedEvent<Integer>> gold = new ArrayList<>();
    private final List<TimedEvent<Integer>> level = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param summonerId the summoner id
     */
    public PlayerTimeline(SummonerId summonerId) {
        this.summonerId = Objects.requireNonNull(summonerId);
    }

    public SummonerId getSummonerId() {
        return summonerId;
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
