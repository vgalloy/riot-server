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
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.service.api.model.LastGame;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;

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
     * Get the ranked stats.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @return the ranked stats
     */
    @RequestMapping(value = "/summoner/{region}/{summonerId}/rankedStats", method = RequestMethod.GET)
    public Model<RankedStatsDto> getRankedStat(@PathVariable Region region, @PathVariable Long summonerId) {
        LOGGER.info("[ GET ] : getRankedStat, region : {}, summonerId : {}", region, summonerId);
        Optional<Model<RankedStatsDto>> rankedStatsEntity = rankedStatsService.get(new DpoId(region, summonerId));
        return rankedStatsEntity.orElse(null);
    }

    /**
     * Get the last games of a summoner during the period [from, to]. Game sorted by date.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @param fromMillis the start search date in millis
     * @param toMillis   the end search date in millis
     * @return the last games
     */
    @RequestMapping(value = "/summoner/{region}/{summonerId}/lastGames", method = RequestMethod.GET)
    public List<LastGame> getLastGames(@PathVariable Region region, @PathVariable Long summonerId, @RequestParam(required = false) Long fromMillis, @RequestParam(required = false) Long toMillis) {
        LOGGER.info("[ GET ] : getLastGames, region : {}, summonerId : {}, fromMillis : {}, toMillis : {}", region, summonerId, fromMillis, toMillis);
        LocalDateTime fromLocalDateTime = Optional.ofNullable(fromMillis)
                .map(e -> LocalDateTime.ofEpochSecond(e / 1000, 0, ZoneOffset.UTC))
                .orElseGet(() -> LocalDateTime.now().minus(1, ChronoUnit.WEEKS));
        LocalDateTime toLocalDateTime = Optional.ofNullable(toMillis)
                .map(e -> LocalDateTime.ofEpochSecond(e / 1000, 0, ZoneOffset.UTC))
                .orElseGet(LocalDateTime::now);

        return summonerService.getLastGames(region, summonerId, fromLocalDateTime, toLocalDateTime);
    }

    /**
     * Get the summoner by name.
     *
     * @param region       the region
     * @param summonerName the summoner name
     * @return the last games
     */
    @RequestMapping(value = "/summoner/{region}/{summonerName}/byName", method = RequestMethod.GET)
    public SummonerDto getSummonerByName(@PathVariable Region region, @PathVariable String summonerName) {
        LOGGER.info("[ GET ] : getSummonerByName, region : {}, summonerName : {}", region, summonerName);
        Optional<SummonerDto> optionalSummonerDto = summonerService.getSummonerByName(region, summonerName);
        return optionalSummonerDto.orElse(null);
    }

    /**
     * Get the summoner by id.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @return the last games
     */
    @RequestMapping(value = "/summoner/{region}/{summonerId}/byId", method = RequestMethod.GET)
    public SummonerDto getSummonerById(@PathVariable Region region, @PathVariable Long summonerId) {
        LOGGER.info("[ GET ] : getSummonerById, region : {}, summonerId : {}", region, summonerId);
        Optional<Model<SummonerDto>> optionalSummonerDto = summonerService.get(new DpoId(region, summonerId));
        return optionalSummonerDto.map(Model::getItem).orElse(null);
    }
}
