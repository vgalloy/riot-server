package vgalloy.riot.server.service.internal.service.mapper;

import java.util.List;
import java.util.Optional;

import vgalloy.riot.api.api.constant.QueueType;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.Participant;
import vgalloy.riot.server.service.api.model.game.GameSummary;
import vgalloy.riot.server.service.internal.service.helper.MatchDetailHelper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class GameSummaryMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private GameSummaryMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a matchDetail into a GameSummary.
     *
     * @param matchDetail the match Detail
     * @param summonerId  the summoner id
     * @return the GameSummary
     */
    public static Optional<GameSummary> map(MatchDetail matchDetail, long summonerId) {
        Optional<Participant> optionalParticipant = MatchDetailHelper.getParticipant(matchDetail, summonerId);

        if (!optionalParticipant.isPresent()) {
            return Optional.empty();
        }

        Participant participant = optionalParticipant.get();

        int championId = participant.getChampionId();
        long kill = participant.getStats().getKills();
        long death = participant.getStats().getDeaths();
        long assist = participant.getStats().getAssists();
        boolean winner = participant.getStats().isWinner();
        long matchCreation = matchDetail.getMatchCreation();
        QueueType queueType = matchDetail.getQueueType();
        int spell1Id = participant.getSpell1Id();
        int spell2Id = participant.getSpell2Id();
        List<Integer> itemIdList = ItemListMapper.map(matchDetail.getTimeline(), participant.getParticipantId());

        GameSummary gameSummary = new GameSummary(GameIdMapper.map(matchDetail), championId, kill, death, assist, winner, matchCreation, queueType, spell1Id, spell2Id, itemIdList);
        return Optional.of(gameSummary);
    }
}
