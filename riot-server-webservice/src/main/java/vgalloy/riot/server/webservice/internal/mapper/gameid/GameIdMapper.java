package vgalloy.riot.server.webservice.internal.mapper.gameid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.game.GameId;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class GameIdMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Constructor.
     * To prevent instantiation
     */
    private GameIdMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a {@link GameId} into a String.
     *
     * @param gameId the game id
     * @return the normalized string of the match detail
     */
    public static String map(GameId gameId) {
        Objects.requireNonNull(gameId);
        return gameId.getRegion() + "_" + gameId.getId() + "_" + gameId.getMatchDate().format(DATE_TIME_FORMATTER);
    }

    /**
     * Convert a String into a MatchDetailId.
     *
     * @param gameId the game id as string.
     * @return the normalized string of the match detail
     */
    public static GameId map(String gameId) {
        Objects.requireNonNull(gameId);

        String[] split = gameId.split("_");
        if (split.length != 3) {
            throw new IllegalArgumentException("the String : " + gameId + " can not be convert into MatchDetailId");
        }
        try {
            Region region = Region.valueOf(split[0]);
            Long matchId = new Long(split[1]);
            LocalDate localDate = LocalDate.from(DATE_TIME_FORMATTER.parse(split[2]));
            return new GameId(region, matchId, localDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("the String : " + gameId + " can not be convert into MatchDetailId");
        }
    }
}
