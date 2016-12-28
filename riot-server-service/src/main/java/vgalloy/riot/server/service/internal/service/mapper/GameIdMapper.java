package vgalloy.riot.server.service.internal.service.mapper;

import java.time.LocalDate;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.service.api.model.game.GameId;

/**
 * @author Vincent Galloy - 28/12/16
 *         Created by Vincent Galloy on 28/12/16.
 */
public final class GameIdMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private GameIdMapper() {
        throw new AssertionError();
    }

    /**
     * Extract {@link GameId} from match detail.
     *
     * @param matchDetail the match detail
     * @return the game id
     */
    public static GameId map(MatchDetail matchDetail) {
        LocalDate matchCreation = LocalDate.ofEpochDay(matchDetail.getMatchCreation() / 1000 / 3600 / 24);
        return new GameId(matchDetail.getRegion(), matchDetail.getMatchId(), matchCreation);
    }
}
