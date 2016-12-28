package vgalloy.riot.server.dao.api.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/09/16.
 */
public final class MatchDetailIdMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Constructor.
     * To prevent instantiation
     */
    private MatchDetailIdMapper() {
        throw new AssertionError();
    }

    /**
     * Extract MatchDetailId from a match reference.
     *
     * @param matchReference the match reference
     * @return the match detail
     */
    public static MatchDetailId map(MatchReference matchReference) {
        Objects.requireNonNull(matchReference);

        Region region = Region.valueOf(matchReference.getRegion());
        Long matchId = matchReference.getMatchId();
        LocalDate matchDate = LocalDate.ofEpochDay(matchReference.getTimestamp() / 1000 / 3600 / 24);

        return new MatchDetailId(region, matchId, matchDate);
    }

    /**
     * Extract MatchDetailId from a matchDetail.
     *
     * @param matchDetail the match detail
     * @return the match detail
     */
    public static MatchDetailId map(MatchDetail matchDetail) {
        Objects.requireNonNull(matchDetail);

        Region region = matchDetail.getRegion();
        Long matchId = matchDetail.getMatchId();
        LocalDate matchDate = LocalDate.ofEpochDay(matchDetail.getMatchCreation() / 1000 / 3600 / 24);

        return new MatchDetailId(region, matchId, matchDate);
    }
}
