package vgalloy.riot.server.webservice.api.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
import vgalloy.riot.server.service.api.service.ChampionService;
import vgalloy.riot.server.webservice.internal.model.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.model.ResourceNotLoadedException;

/**
 * Created by Vincent Galloy on 13/06/16.
 *
 * @author Vincent Galloy
 */
@RestController
public class ChampionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionController.class);

    private final ChampionService championService;

    /**
     * Constructor.
     *
     * @param championService the championService
     */
    @Autowired
    public ChampionController(ChampionService championService) {
        this.championService = Objects.requireNonNull(championService);
    }

    /**
     * Get champion information.
     *
     * @param championId the champion id
     * @param region     the region for the champion information (default EUW)
     * @return the champion information
     */
    @RequestMapping(value = "/champions/{championId}", method = RequestMethod.GET)
    ChampionDto getChampion(@PathVariable Long championId, @RequestParam(required = false) Region region) {
        LOGGER.info("[ GET ] : getChampion : {}, Region : {}", championId, region);
        Region computedRegion = Optional.ofNullable(region).orElse(Region.EUW);
        return championService.get(new DpoId(computedRegion, championId))
                .ifNotLoadedThrow(ResourceNotLoadedException::new)
                .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }

    /**
     * Get the win rate of a champion as a mapToEntity. The key is the number of game played.
     *
     * @param championId the champion id
     * @return the win rates as a mapToEntity
     */
    @RequestMapping(value = "/champions/{championId}/winRateByGamePlayed", method = RequestMethod.GET)
    Map<Integer, Double> getWinRateByGamePlayed(@PathVariable Integer championId) {
        LOGGER.info("[ GET ] : getWinRateByGamePlayed : {}", championId);
        return championService.getWinRateByGamePlayed(championId);
    }

    /**
     * Get the win rate of a champion as a mapToEntity. The key is the number of game played.
     *
     * @param championId the champion id
     * @param fromDay    the start search date in day
     * @param toDay      the end search date in day
     * @return the win rates as a mapToEntity
     */
    @RequestMapping(value = "/champions/{championId}/winRateByDate", method = RequestMethod.GET)
    Map<Long, WinRate> getWinRateDuringPeriodOfTime(@PathVariable Integer championId,
                                                           @RequestParam(required = false) Long fromDay,
                                                           @RequestParam(required = false) Long toDay) {
        LOGGER.info("[ GET ] : getWinRateDuringPeriodOfTime, championId : {},  fromDay : {}, toDay : {}", championId, fromDay, toDay);
        LocalDate fromLocalDate = Optional.ofNullable(fromDay)
                .map(LocalDate::ofEpochDay)
                .orElseGet(() -> LocalDate.now().minus(1, ChronoUnit.WEEKS));
        LocalDate toLocalDate = Optional.ofNullable(toDay)
                .map(LocalDate::ofEpochDay)
                .orElseGet(LocalDate::now);

        Map<Long, WinRate> result = new HashMap<>();
        for (Map.Entry<LocalDate, WinRate> entry : championService.getWinRateDuringPeriodOfTime(championId, fromLocalDate, toLocalDate).entrySet()) {
            result.put(entry.getKey().toEpochDay(), entry.getValue());
        }
        return result;
    }

    /**
     * Get the win rate for all champion for the given day.
     *
     * @param day the day to analyse
     * @return a map (champion Id, win rate)
     */
    @RequestMapping(value = "/champions/winRateByDate", method = RequestMethod.GET)
    Map<Integer, WinRate> getWinRateForAllChampion(@RequestParam(required = false) Long day) {
        LOGGER.info("[ GET ] : getWinRateForAllChampion, day : {}", day);
        LocalDate localDate = Optional.ofNullable(day)
                .map(LocalDate::ofEpochDay)
                .orElseGet(LocalDate::now);
        return championService.getWinRateForAllChampion(localDate);
    }
}
