package vgalloy.riot.server.webservice.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.MatchDetailService;

import java.util.Optional;

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
    @RequestMapping(value = "/game/{region}/{matchId}", method = RequestMethod.GET)
    public Model<MatchDetail> getMatchDetail(@PathVariable Region region, @PathVariable long matchId) {
        LOGGER.info("[ GET ] : region : {}, matchId : {}", region, matchId);
        Optional<Model<MatchDetail>> matchDetailEntity = matchDetailService.get(region, matchId);
        if (matchDetailEntity.isPresent()) {
            return matchDetailEntity.get();
        }
        return null;
    }
}
