package vgalloy.riot.server.webservice.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vgalloy.riot.server.service.api.service.QueryService;

import java.util.Map;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
@RestController
public class ChampionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionController.class);

    @Autowired
    private QueryService queryService;

    /**
     * Get the win rate of a champion as a map. The key is the number of game played.
     *
     * @param championId the champion id
     * @return the win rates as a map
     */
    @RequestMapping(value = "/champion/{championId}/winRate", method = RequestMethod.GET)
    public Map<Integer, Double> getWinRate(@PathVariable int championId) {
        LOGGER.info("[ GET ] : championId : {}", championId);
        return queryService.getWinRate(championId);
    }
}
