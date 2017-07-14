package com.vgalloy.riot.server.webservice.internal.controller.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;

import com.vgalloy.riot.server.dao.api.entity.ChampionName;
import com.vgalloy.riot.server.dao.api.entity.WinRate;
import com.vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import com.vgalloy.riot.server.service.api.service.ChampionService;
import com.vgalloy.riot.server.service.api.service.exception.UserException;
import com.vgalloy.riot.server.webservice.api.controller.ChampionController;
import com.vgalloy.riot.server.webservice.api.dto.impl.AutoCompleteChampionNameDto;
import com.vgalloy.riot.server.webservice.api.dto.impl.ChampionNameDto;
import com.vgalloy.riot.server.webservice.api.dto.impl.WinRateDto;
import com.vgalloy.riot.server.webservice.internal.exception.ResourceDoesNotExistException;
import com.vgalloy.riot.server.webservice.internal.exception.ResourceNotLoadedException;
import com.vgalloy.riot.server.webservice.internal.mapper.Mapper;

/**
 * Created by Vincent Galloy on 13/06/16.
 *
 * @author Vincent Galloy
 */
@RestController
@RequestMapping(value = "/champions")
final class ChampionControllerImpl implements ChampionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChampionControllerImpl.class);

    private final ChampionService championService;
    private final Mapper<ChampionName, ChampionNameDto> championNameMapper;
    private final Mapper<WinRate, WinRateDto> winRateMapper;

    /**
     * Constructor.
     *
     * @param championService    the championService
     * @param championNameMapper the championNameMapper
     * @param winRateMapper      the winRateMapper
     */
    ChampionControllerImpl(ChampionService championService, Mapper<ChampionName, ChampionNameDto> championNameMapper, Mapper<WinRate, WinRateDto> winRateMapper) {
        this.championService = Objects.requireNonNull(championService);
        this.championNameMapper = Objects.requireNonNull(championNameMapper);
        this.winRateMapper = Objects.requireNonNull(winRateMapper);
    }

    @Override
    @GetMapping("/{championId}")
    public ChampionDto getChampion(@PathVariable Long championId, @RequestParam(required = false) Region region) {
        LOGGER.info("[ GET ] : getChampion : {}, Region : {}", championId, region);

        Region computedRegion = Optional.ofNullable(region).orElse(Region.EUW);
        return championService.get(new CommonDpoId(computedRegion, championId))
            .ifNotLoadedThrow(ResourceNotLoadedException::new)
            .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }

    @Override
    @GetMapping("/{championId}/winRateByGamePlayed")
    public Map<Integer, Double> getWinRateByGamePlayed(@PathVariable Integer championId) {
        LOGGER.info("[ GET ] : getWinRateByGamePlayed : {}", championId);

        return championService.getWinRateByGamePlayed(championId);
    }

    @Override
    @GetMapping("/{championId}/winRateByDate")
    public Map<Long, WinRateDto> getWinRateDuringPeriodOfTime(@PathVariable Integer championId,
                                                              @RequestParam(required = false) Long fromDay,
                                                              @RequestParam(required = false) Long toDay) {
        LOGGER.info("[ GET ] : getWinRateDuringPeriodOfTime, championId : {},  fromDay : {}, toDay : {}", championId, fromDay, toDay);

        LocalDate fromLocalDate = Optional.ofNullable(fromDay)
            .map(LocalDate::ofEpochDay)
            .orElseGet(() -> LocalDate.now().minus(1, ChronoUnit.WEEKS));
        LocalDate toLocalDate = Optional.ofNullable(toDay)
            .map(LocalDate::ofEpochDay)
            .orElseGet(LocalDate::now);
        return championService.getWinRateDuringPeriodOfTime(championId, fromLocalDate, toLocalDate)
            .entrySet().stream()
            .collect(Collectors.toMap(
                entry -> entry.getKey().toEpochDay(),
                entry -> winRateMapper.map(entry.getValue())
            ));
    }

    @Override
    @GetMapping("/winRateByDate")
    public Map<Integer, WinRateDto> getWinRateForAllChampion(@RequestParam(required = false) Long day) {
        LOGGER.info("[ GET ] : getWinRateForAllChampion, day : {}", day);

        LocalDate localDate = Optional.ofNullable(day)
            .map(LocalDate::ofEpochDay)
            .orElseGet(LocalDate::now);
        return championService.getWinRateForAllChampion(localDate)
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> winRateMapper.map(entry.getValue())
            ));
    }

    @Override
    @PostMapping("/autoCompleteChampionName")
    public List<ChampionNameDto> autoCompleteChampionName(@RequestBody AutoCompleteChampionNameDto autoCompleteChampionNameDto) {
        LOGGER.info("[ GET ] : autoCompleteChampionName, autoCompleteChampionNameDto : {}", autoCompleteChampionNameDto);

        UserException.requireNonNull(autoCompleteChampionNameDto, "Body can not be null");
        UserException.requireNonNull(autoCompleteChampionNameDto.getRegion(), "Region can not be null");
        UserException.requireNonNull(autoCompleteChampionNameDto.getName(), "Name can not be null");
        List<ChampionName> result = championService.autoCompleteChampionName(autoCompleteChampionNameDto.getRegion(), autoCompleteChampionNameDto.getName());

        return championNameMapper.mapList(result);
    }
}
