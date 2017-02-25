package vgalloy.riot.server.service.api.model.game;

import java.time.LocalDate;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 28/12/16
 *         Created by Vincent Galloy on 28/12/16.
 */
public final class GameId {

    private final Region region;
    private final Long id;
    private final LocalDate matchDate;

    /**
     * Constructor.
     *
     * @param region    the region
     * @param id        the id
     * @param matchDate the match date
     */
    public GameId(Region region, Long id, LocalDate matchDate) {
        this.region = Objects.requireNonNull(region);
        this.id = Objects.requireNonNull(id);
        this.matchDate = Objects.requireNonNull(matchDate);
    }

    public Region getRegion() {
        return region;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }
}
