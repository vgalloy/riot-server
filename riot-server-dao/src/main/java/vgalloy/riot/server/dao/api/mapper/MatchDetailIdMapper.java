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
     * Convert a MatchDetailId into a String.
     *
     * @param matchDetailId the match detail id
     * @return the normalized string of the match detail
     */
    public static String map(MatchDetailId matchDetailId) {
        Objects.requireNonNull(matchDetailId);
        return matchDetailId.getRegion() + "_" + matchDetailId.getId() + "_" + matchDetailId.getMatchDate().format(DATE_TIME_FORMATTER);
    }

    /**
     * Convert a String into a MatchDetailId.
     *
     * @param matchDetailId the match detail id as string.
     * @return the normalized string of the match detail
     */
    public static MatchDetailId map(String matchDetailId) { // TODO Exception ?
        Objects.requireNonNull(matchDetailId);

        String[] split = matchDetailId.split("_");
        if (split.length != 3) {
            throw new IllegalArgumentException("the String : " + matchDetailId + " can not be convert into MatchDetailId");
        }
        try {
            Region region = Region.valueOf(split[0]);
            Long matchId = new Long(split[1]);
            LocalDate localDate = LocalDate.from(DATE_TIME_FORMATTER.parse(split[2]));
            return new MatchDetailId(region, matchId, localDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("the String : " + matchDetailId + " can not be convert into MatchDetailId");
        }
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
     * Extract MatchDetailId from a matchdetail.
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
