package vgalloy.riot.server.service.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.service.api.model.GameInformation;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.model.PlayerTimeline;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/06/16.
 */
public interface MatchDetailService {

    /**
     * Get the match .
     *
     * @param region    the region
     * @param itemId    the item id
     * @param matchDate the match date
     * @return the datable
     */
    Optional<Model<MatchDetail>> get(Region region, Long itemId, LocalDate matchDate);

    /**
     * Get the time line for each player in the game.
     *
     * @param region    the region of the game
     * @param matchId   the match id
     * @param matchDate the matchDate
     * @return the time lines
     */
    Optional<List<PlayerTimeline>> getTimeLines(Region region, long matchId, LocalDate matchDate);

    /**
     * Get all the games of a summoner during the period : [startSearchDate, endSearchDate[. The game ARE sorted
     *
     * @param region          the region
     * @param summonerId      the summoner id
     * @param matchDate the matchDate
     * @return the last games
     */
    List<GameInformation> getMatchInformation(Region region, long summonerId, LocalDate matchDate);
}
