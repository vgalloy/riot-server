package vgalloy.riot.server.webservice.api.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.ChampionService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
@RestController
public class ChampionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionController.class);

    @Autowired
    private ChampionService championService;

    /**
     * Get champion information.
     *
     * @param region     the region for the champion information
     * @param championId the champion id
     * @return the champion information
     */
    @RequestMapping(value = "/champion/{region}/{championId}", method = RequestMethod.GET)
    public Model<ChampionDto> getChampion(@PathVariable Region region, @PathVariable Long championId) {
        LOGGER.info("[ GET ] : getChampion : {}", championId);
        Optional<Model<ChampionDto>> result = championService.get(new DpoId(region, championId));
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    /**
     * Get the win rate of a champion as a mapToEntity. The key is the number of game played.
     *
     * @param championId the champion id
     * @return the win rates as a mapToEntity
     */
    @RequestMapping(value = "/champion/{championId}/winRateByGamePlayed", method = RequestMethod.GET)
    public Map<Integer, Double> getWinRateByGamePlayed(@PathVariable Integer championId) {
        LOGGER.info("[ GET ] : getWinRateByGamePlayed : {}", championId);
        return championService.getWinRate(championId);
    }

    /**
     * Get the win rate of a champion as a mapToEntity. The key is the number of game played.
     *
     * @param championId the champion id
     * @param fromMillis the start search date in millis
     * @param toMillis   the end search date in millis
     * @return the win rates as a mapToEntity
     */
    @RequestMapping(value = "/champion/{championId}/winRateByDate", method = RequestMethod.GET)
    public Map<Long, WinRate> getWinRateDuringPeriodOfTime(@PathVariable Integer championId, @RequestParam Long fromMillis, @RequestParam Long toMillis) {
        LOGGER.info("[ GET ] : getWinRateDuringPeriodOfTime, championId : {},  fromMillis : {}, toMillis : {}", championId, fromMillis, toMillis);
        LocalDate fromLocalDate = LocalDate.ofEpochDay(fromMillis / 1000 / 3600 / 24);
        LocalDate toLocalDate = LocalDate.ofEpochDay(toMillis / 1000 / 3600 / 24);

        Map<Long, WinRate> result = new HashMap<>();
        for (Map.Entry<LocalDate, WinRate> entry : championService.getWinRate(championId, fromLocalDate, toLocalDate).entrySet()) {
            result.put(entry.getKey().toEpochDay(), entry.getValue());
        }
        return result;
    }
}
