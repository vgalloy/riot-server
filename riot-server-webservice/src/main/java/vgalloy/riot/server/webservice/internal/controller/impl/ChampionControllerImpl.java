package vgalloy.riot.server.webservice.internal.controller.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import vgalloy.riot.server.service.api.service.ChampionService;
import vgalloy.riot.server.webservice.api.controller.ChampionController;
import vgalloy.riot.server.webservice.internal.model.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.model.ResourceNotLoadedException;

/**
 * Created by Vincent Galloy on 13/06/16.
 *
 * @author Vincent Galloy
 */
@RestController
public class ChampionControllerImpl implements ChampionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionControllerImpl.class);

    private final ChampionService championService;

    /**
     * Constructor.
     *
     * @param championService the championService
     */
    public ChampionControllerImpl(ChampionService championService) {
        this.championService = Objects.requireNonNull(championService);
    }

    @Override
    @RequestMapping(value = "/champions/{championId}", method = RequestMethod.GET)
    public ChampionDto getChampion(@PathVariable Long championId, @RequestParam(required = false) Region region) {
        LOGGER.info("[ GET ] : getChampion : {}, Region : {}", championId, region);
        Region computedRegion = Optional.ofNullable(region).orElse(Region.EUW);
        return championService.get(new CommonDpoId(computedRegion, championId))
            .ifNotLoadedThrow(ResourceNotLoadedException::new)
            .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }

    @Override
    @RequestMapping(value = "/champions/{championId}/winRateByGamePlayed", method = RequestMethod.GET)
    public Map<Integer, Double> getWinRateByGamePlayed(@PathVariable Integer championId) {
        LOGGER.info("[ GET ] : getWinRateByGamePlayed : {}", championId);
        return championService.getWinRateByGamePlayed(championId);
    }

    @Override
    @RequestMapping(value = "/champions/{championId}/winRateByDate", method = RequestMethod.GET)
    public Map<Long, WinRate> getWinRateDuringPeriodOfTime(@PathVariable Integer championId,
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
        championService.getWinRateDuringPeriodOfTime(championId, fromLocalDate, toLocalDate)
            .forEach((key, value) -> result.put(key.toEpochDay(), value));
        return result;
    }

    @Override
    @RequestMapping(value = "/champions/winRateByDate", method = RequestMethod.GET)
    public Map<Integer, WinRate> getWinRateForAllChampion(@RequestParam(required = false) Long day) {
        LOGGER.info("[ GET ] : getWinRateForAllChampion, day : {}", day);
        LocalDate localDate = Optional.ofNullable(day)
            .map(LocalDate::ofEpochDay)
            .orElseGet(LocalDate::now);
        return championService.getWinRateForAllChampion(localDate);
    }
}
