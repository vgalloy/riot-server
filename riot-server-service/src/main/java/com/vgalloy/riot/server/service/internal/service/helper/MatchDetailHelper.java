package com.vgalloy.riot.server.service.internal.service.helper;

import java.util.Optional;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.Participant;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;

/**
 * Created by Vincent Galloy on 28/08/16.
 *
 * @author Vincent Galloy
 */
public final class MatchDetailHelper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private MatchDetailHelper() {
        throw new AssertionError();
    }

    /**
     * Get the participant id. Empty if the player doesn't play this game.
     *
     * @param participantIdentities the participant list
     * @param summonerId            the summoner id
     * @return the participant id
     */
    private static Optional<Integer> getParticipantId(Iterable<ParticipantIdentity> participantIdentities, long summonerId) {
        for (ParticipantIdentity participantIdentity : participantIdentities) {
            if (summonerId == participantIdentity.getPlayer().getSummonerId()) {
                return Optional.of(participantIdentity.getParticipantId());
            }
        }
        return Optional.empty();
    }

    /**
     * Get the participant. Empty if the player doesn't play this game.
     *
     * @param matchDetail the match detail
     * @param summonerId  the summoner id
     * @return the participant
     */
    public static Optional<Participant> getParticipant(MatchDetail matchDetail, long summonerId) {
        Optional<Integer> integer = getParticipantId(matchDetail.getParticipantIdentities(), summonerId);
        if (integer.isPresent()) {
            for (Participant participant : matchDetail.getParticipants()) {
                if (integer.get().equals(participant.getParticipantId())) {
                    return Optional.of(participant);
                }
            }
        }
        return Optional.empty();
    }
}
