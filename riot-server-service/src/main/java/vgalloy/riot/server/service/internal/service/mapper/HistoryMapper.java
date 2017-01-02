package vgalloy.riot.server.service.internal.service.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vgalloy.riot.api.api.dto.mach.Frame;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.ParticipantFrame;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;
import vgalloy.riot.api.api.dto.mach.Timeline;
import vgalloy.riot.server.service.api.model.game.PlayerTimeline;
import vgalloy.riot.server.service.api.model.game.TimedEvent;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/09/16.
 */
public final class HistoryMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private HistoryMapper() {
        throw new AssertionError();
    }

    /**
     * Extract the list of playerTimeline from the Timeline.
     *
     * @param matchDetail the matchDetail
     * @return the list of PlayerTimeline
     */
    public static List<PlayerTimeline> map(MatchDetail matchDetail) {
        Timeline timeline = matchDetail.getTimeline();
        List<ParticipantIdentity> participantIdentities = matchDetail.getParticipantIdentities();

        if (timeline == null || participantIdentities == null) {
            return new ArrayList<>();
        }

        Map<Integer, PlayerTimeline> result = new HashMap<>();
        for (ParticipantIdentity participantIdentity : participantIdentities) {
            result.put(participantIdentity.getParticipantId(), new PlayerTimeline(new SummonerId(matchDetail.getRegion(), participantIdentity.getPlayer().getSummonerId())));
        }

        for (Frame frame : timeline.getFrames()) {
            for (Map.Entry<String, ParticipantFrame> entry : frame.getParticipantFrames().entrySet()) {
                int participantId = Integer.valueOf(entry.getKey());

                result.get(participantId).getFarming().add(new TimedEvent<>(frame.getTimestamp(), entry.getValue().getMinionsKilled()));
                if (entry.getValue().getPosition() != null) { // TODO chercher pourquoi certaines positions ne marche pas
                    result.get(participantId).getPosition().add(new TimedEvent<>(frame.getTimestamp(), PositionMapper.map(entry.getValue().getPosition())));
                }
                result.get(participantId).getGold().add(new TimedEvent<>(frame.getTimestamp(), entry.getValue().getTotalGold()));
                result.get(participantId).getLevel().add(new TimedEvent<>(frame.getTimestamp(), entry.getValue().getLevel()));
            }
        }
        return new ArrayList<>(result.values());
    }
}
