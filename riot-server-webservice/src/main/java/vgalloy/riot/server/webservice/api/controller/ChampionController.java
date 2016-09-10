package vgalloy.riot.server.webservice.api.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.service.api.service.QueryService;

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
    public Map<Integer, Double> getWinRateByGamePlayed(@PathVariable Integer championId) {
        LOGGER.info("[ GET ] : getWinRateByGamePlayed : {}", championId);
        return queryService.getWinRate(championId);
    }

    /**
     * Get the win rate of a champion as a map. The key is the number of game played.
     *
     * @param championId the champion id
     * @param startDay   the start date (included)
     * @param endDay     the end date (excluded)
     * @return the win rates as a map
     */
    @RequestMapping(value = "/champion/{championId}/winRate/{startDay}/{endDay}", method = RequestMethod.GET)
    public Map<Long, WinRate> getWinRateDuringPeriodOfTime(@PathVariable Integer championId, @PathVariable Long startDay, @PathVariable Long endDay) {
        LOGGER.info("[ GET ] : getWinRateDuringPeriodOfTime : {} {} {}", championId, startDay, endDay);
        Map<Long, WinRate> result = new HashMap<>();
        for (Map.Entry<LocalDate, WinRate> entry : queryService.getWinRate(championId, LocalDate.ofEpochDay(startDay), LocalDate.ofEpochDay(endDay)).entrySet()) {
            result.put(entry.getKey().toEpochDay(), entry.getValue());
        }
        return result;
    }
}
