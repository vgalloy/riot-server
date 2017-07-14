package com.vgalloy.riot.server.webservice.internal.controller.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.dao.api.entity.GetSummonersQuery;
import com.vgalloy.riot.server.service.api.model.summoner.GameSummary;
import com.vgalloy.riot.server.service.api.model.summoner.RankedStats;
import com.vgalloy.riot.server.service.api.model.summoner.Summoner;
import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;
import com.vgalloy.riot.server.service.api.service.RankedStatsService;
import com.vgalloy.riot.server.service.api.service.SummonerService;
import com.vgalloy.riot.server.webservice.api.controller.SummonerController;
import com.vgalloy.riot.server.webservice.internal.exception.ResourceDoesNotExistException;
import com.vgalloy.riot.server.webservice.internal.exception.ResourceNotLoadedException;

/**
 * Created by Vincent Galloy on 20/06/16.
 *
 * @author Vincent Galloy
 */
@RestController
final class SummonerControllerImpl implements SummonerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerControllerImpl.class);

    private final SummonerService summonerService;
    private final RankedStatsService rankedStatsService;

    /**
     * Constructor.
     *
     * @param summonerService    the summonerService
     * @param rankedStatsService the rankedStatsService
     */
    SummonerControllerImpl(SummonerService summonerService, RankedStatsService rankedStatsService) {
        this.summonerService = Objects.requireNonNull(summonerService);
        this.rankedStatsService = Objects.requireNonNull(rankedStatsService);
    }

    @Override
    @GetMapping("/summoners")
    public List<Summoner> getSummoners(@RequestParam List<Region> regions,
                                       @RequestParam(required = false) List<String> summonerNames,
                                       @RequestParam(required = false) Integer offset,
                                       @RequestParam(required = false) Integer limit) {
        LOGGER.info("[ GET ] : getSummonerByName, regions : {}, summonerNames : {}, offset : {}, limit : {}", regions, summonerNames, offset, limit);
        GetSummonersQuery query = GetSummonersQuery.build();
        Optional.ofNullable(regions).ifPresent(query::addRegions);
        Optional.ofNullable(summonerNames).ifPresent(query::addSummonersName);
        Optional.ofNullable(offset).ifPresent(query::setOffset);
        Optional.ofNullable(limit).ifPresent(query::setLimit);

        return summonerService.getSummoners(query);
    }

    @Override
    @GetMapping("/summoners/{summonerId}")
    public Summoner getSummonerById(@PathVariable SummonerId summonerId) {
        LOGGER.info("[ GET ] : getSummonerById, summonerId : {}", summonerId);
        return summonerService.get(summonerId)
            .ifNotLoadedThrow(ResourceNotLoadedException::new)
            .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }

    @Override
    @GetMapping("/summoners/{summonerId}/rankedStats")
    public RankedStats getRankedStat(@PathVariable SummonerId summonerId) {
        LOGGER.info("[ GET ] : getRankedStat, region : {}, summonerId : {}", summonerId);
        return rankedStatsService.get(summonerId)
            .ifNotLoadedThrow(ResourceNotLoadedException::new)
            .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }

    @Override
    @GetMapping("/summoners/{summonerId}/lastGames")
    public List<GameSummary> getLastGames(@PathVariable SummonerId summonerId,
                                          @RequestParam(required = false) Long fromDay,
                                          @RequestParam(required = false) Long toDay) {
        LOGGER.info("[ GET ] : getLastGames, region : {}, summonerId : {}, fromDay : {}, toDay : {}", summonerId, fromDay, toDay);
        LocalDateTime fromLocalDateTime = Optional.ofNullable(fromDay)
            .map(e -> LocalDateTime.ofEpochSecond(fromDay * 24 * 3600, 0, ZoneOffset.UTC))
            .orElseGet(() -> LocalDateTime.now().minus(1, ChronoUnit.WEEKS));
        LocalDateTime toLocalDateTime = Optional.ofNullable(toDay)
            .map(e -> LocalDateTime.ofEpochSecond(toDay * 24 * 3600, 0, ZoneOffset.UTC))
            .orElseGet(LocalDateTime::now);

        return summonerService.getLastGames(summonerId, fromLocalDateTime, toLocalDateTime);
    }
}
