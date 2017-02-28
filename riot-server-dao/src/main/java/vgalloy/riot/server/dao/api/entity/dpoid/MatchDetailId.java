package vgalloy.riot.server.dao.api.entity.dpoid;

import java.time.LocalDate;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 07/10/16.
 *
 * @author Vincent Galloy
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
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatchDetailId that = (MatchDetailId) o;
        return Objects.equals(matchDate, that.matchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchDate);
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
