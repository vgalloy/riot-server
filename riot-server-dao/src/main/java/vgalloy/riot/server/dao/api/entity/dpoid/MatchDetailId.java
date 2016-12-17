package vgalloy.riot.server.dao.api.entity.dpoid;

import java.time.LocalDate;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public class MatchDetailId extends DpoId {

    private static final long serialVersionUID = 536360614566269643L;

    private final LocalDate matchDate;

    /**
     * Constructor.
     *
     * @param region    the region
     * @param id        the id
     * @param matchDate the match creation date
     */
    public MatchDetailId(Region region, Long id, LocalDate matchDate) {
        super(region, id);
        this.matchDate = matchDate;
        if (matchDate.isBefore(LocalDate.of(2010, 1, 1)) || matchDate.isAfter(LocalDate.of(2020, 1, 1))) {
            throw new IllegalArgumentException("the date " + matchDate + " is out of possible match range");
        }
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    @Override
    public String toString() {
        return "MatchDetailId{" +
                "region=" + getRegion() +
                ", id=" + getId() +
                ", matchDate=" + matchDate +
                "}";
    }
}
