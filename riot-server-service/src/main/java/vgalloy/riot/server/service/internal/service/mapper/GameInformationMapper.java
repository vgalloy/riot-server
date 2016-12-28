package vgalloy.riot.server.service.internal.service.mapper;

import java.util.List;
import java.util.Objects;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.service.api.model.game.GameInformation;
import vgalloy.riot.server.service.api.model.game.PlayerTimeline;
import vgalloy.riot.server.service.api.model.game.SummonerInformation;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public final class GameInformationMapper {

    /**
     * Constructor.
     * <p>
     * To prevent instantiation
     */
    private GameInformationMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a matchDetail into a game information.
     *
     * @param matchDetail the match detail
     * @return the Game
     */
    public static GameInformation map(MatchDetail matchDetail) {
        Objects.requireNonNull(matchDetail);

        List<PlayerTimeline> playerTimelines = HistoryMapper.map(matchDetail);
        List<SummonerInformation> summonerInformation = SummonerInformationMapper.map(matchDetail);

        return new GameInformation(playerTimelines, summonerInformation);
    }
}
