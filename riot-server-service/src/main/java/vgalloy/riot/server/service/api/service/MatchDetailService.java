package vgalloy.riot.server.service.api.service;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.service.api.model.PlayerTimeline;

import java.util.List;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/06/16.
 */
public interface MatchDetailService extends CommonService<MatchDetail> {

    /**
     * Get the time line for each player in the game.
     *
     * @param region  the region of the game
     * @param matchId the match id
     * @return the time lines
     */
    Optional<List<PlayerTimeline>> getTimeLines(Region region, long matchId);
}
