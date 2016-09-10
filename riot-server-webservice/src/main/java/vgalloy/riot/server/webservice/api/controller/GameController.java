package vgalloy.riot.server.webservice.api.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.model.PlayerTimeline;
import vgalloy.riot.server.service.api.service.MatchDetailService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
@RestController
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private MatchDetailService matchDetailService;

    /**
     * Get the information about a game.
     *
     * @param region  the region
     * @param matchId the matchId
     * @return the match detail
     */
    @RequestMapping(value = "/game/{region}/{matchId}/detail", method = RequestMethod.GET)
    public Model<MatchDetail> getMatchDetail(@PathVariable Region region, @PathVariable Long matchId) {
        LOGGER.info("[ GET ] : region : {}, matchId : {}", region, matchId);
        Optional<Model<MatchDetail>> optionalResult = matchDetailService.get(region, matchId);
        if (optionalResult.isPresent()) {
            return optionalResult.get();
        }
        return null;
    }

    /**
     * Get the players information during the game.
     *
     * @param region  the region of the game
     * @param matchId the match id
     * @return the players information as a list
     */
    @RequestMapping(value = "/game/{region}/{matchId}/timelines", method = RequestMethod.GET)
    public List<PlayerTimeline> getTimelines(@PathVariable Region region, @PathVariable Long matchId) {
        Optional<List<PlayerTimeline>> optionalResult = matchDetailService.getTimeLines(region, matchId);
        if (optionalResult.isPresent()) {
            return optionalResult.get();
        }
        return null;
    }
}
