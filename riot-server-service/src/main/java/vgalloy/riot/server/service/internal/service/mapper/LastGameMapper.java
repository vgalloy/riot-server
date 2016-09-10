package vgalloy.riot.server.service.internal.service.mapper;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.Participant;
import vgalloy.riot.server.service.api.model.LastGame;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.service.helper.MatchDetailHelper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class LastGameMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private LastGameMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a matchDetail into a LastGame.
     *
     * @param matchDetail the match Detail
     * @param summonerId  the summoner id
     * @return the LastGame
     */
    public static LastGame map(MatchDetail matchDetail, long summonerId) {
        Optional<Participant> optionalParticipant = MatchDetailHelper.getParticipant(matchDetail, summonerId);
        if (!optionalParticipant.isPresent()) {
            throw new ServiceException("The summoner : " + summonerId + "didn't played the game : " + matchDetail.getMatchId());
        }
        Participant participant = optionalParticipant.get();

        Region region = matchDetail.getRegion();
        long gameId = matchDetail.getMatchId();
        int championId = participant.getChampionId();
        long kill = participant.getStats().getKills();
        long death = participant.getStats().getDeaths();
        long assist = participant.getStats().getAssists();

        return new LastGame(region, gameId, championId, kill, death, assist);
    }
}
