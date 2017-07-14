package com.vgalloy.riot.server.service.internal.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.Participant;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;

import com.vgalloy.riot.server.service.api.model.game.SummonerInformation;
import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;
import com.vgalloy.riot.server.service.internal.service.helper.MatchDetailHelper;

/**
 * Created by Vincent Galloy on 09/12/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerInformationMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private SummonerInformationMapper() {
        throw new AssertionError();
    }

    /**
     * Extract playerInformation from match Detail.
     *
     * @param matchDetail the match detail
     * @return the player information
     */
    public static List<SummonerInformation> map(MatchDetail matchDetail) {
        Objects.requireNonNull(matchDetail);

        List<SummonerInformation> result = new ArrayList<>();

        for (ParticipantIdentity participantIdentity : matchDetail.getParticipantIdentities()) {
            Optional<Participant> optionalParticipant = MatchDetailHelper.getParticipant(matchDetail, participantIdentity.getPlayer().getSummonerId());
            if (optionalParticipant.isPresent()) {
                SummonerId summonerId = new SummonerId(matchDetail.getRegion(), participantIdentity.getPlayer().getSummonerId());
                result.add(new SummonerInformation(summonerId, participantIdentity.getPlayer().getSummonerName(), optionalParticipant.get().getChampionId()));
            }
        }

        return result;
    }
}
