package vgalloy.riot.server.webservice.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
import vgalloy.riot.server.dao.internal.dao.impl.summoner.GetSummonersQuery;
import vgalloy.riot.server.service.api.model.summoner.GameSummary;
import vgalloy.riot.server.service.api.model.summoner.RankedStats;
import vgalloy.riot.server.service.api.model.summoner.Summoner;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.webservice.internal.model.ResourceDoesNotExistException;
import vgalloy.riot.server.webservice.internal.model.ResourceNotLoadedException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 20/06/16.
 */
@RestController
public class SummonerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerController.class);

    @Autowired
    private SummonerService summonerService;
    @Autowired
    private RankedStatsService rankedStatsService;

    /**
     * Get the summoner with criteria.
     *
     * @param regions       the region (default ALL)
     * @param summonerNames the summoner name (default ALL)
     * @param offset       the request offset (default 0)
     * @param limit        the max limit (default 10)
     * @return the last games
     */
    @RequestMapping(value = "/summoners", method = RequestMethod.GET)
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

    /**
     * Get the summoner by id.
     *
     * @param summonerId the summoner id
     * @return the last games
     */
    @RequestMapping(value = "/summoners/{summonerId}", method = RequestMethod.GET)
    public Summoner getSummonerById(@PathVariable SummonerId summonerId) {
        LOGGER.info("[ GET ] : getSummonerById, summonerId : {}", summonerId);
        return summonerService.get(summonerId)
                .ifNotLoadedThrow(ResourceNotLoadedException::new)
                .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }

    /**
     * Get the ranked stats.
     *
     * @param summonerId the summoner id
     * @return the ranked stats
     */
    @RequestMapping(value = "/summoners/{summonerId}/rankedStats", method = RequestMethod.GET)
    public RankedStats getRankedStat(@PathVariable SummonerId summonerId) {
        LOGGER.info("[ GET ] : getRankedStat, region : {}, summonerId : {}", summonerId);
        return rankedStatsService.get(summonerId)
                .ifNotLoadedThrow(ResourceNotLoadedException::new)
                .ifDoesNotExistThrow(ResourceDoesNotExistException::new);
    }

    /**
     * Get the last games of a summoner during the period [from, to]. Game sorted by date.
     *
     * @param summonerId the summoner id
     * @param fromDay    the start search date in day
     * @param toDay      the end search date in day
     * @return the last games
     */
    @RequestMapping(value = "/summoners/{summonerId}/lastGames", method = RequestMethod.GET)
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
