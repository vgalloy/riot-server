package vgalloy.riot.server.webservice.api.controller;

import java.time.LocalDate;
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
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
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
        LOGGER.info("[ GET ] : getRankedStat, region : {}, matchId : {}", region, summonerId);
        Optional<Model<RankedStatsDto>> rankedStatsEntity = rankedStatsService.get(new ItemId(region, summonerId));
        if (rankedStatsEntity.isPresent()) {
            return rankedStatsEntity.get();
        }
        return null;
    }

    /**
     * Get the last games of a summoner.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @param from       the start search date in millis
     * @param to         the en search date in millis
     * @return the last games
     */
    @RequestMapping(value = "/summoner/{region}/{summonerId}/lastGames", method = RequestMethod.GET)
    public List<LastGame> getLastGames(@PathVariable Region region, @PathVariable Long summonerId, @RequestParam Long from, @RequestParam Long to) {
        LOGGER.info("[ GET ] : getLastGames, region : {}, summonerId : {}, from : {}, to : {}", region, summonerId, from, to);
        LocalDate fromLocalDate = LocalDate.ofEpochDay(from / 1000 / 3600 / 24);
        LocalDate toLocalDate = LocalDate.ofEpochDay(to / 1000 / 3600 / 24);

        return summonerService.getLastGames(region, summonerId, fromLocalDate, toLocalDate);
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
        if (optionalSummonerDto.isPresent()) {
            return optionalSummonerDto.get();
        }
        return null;
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
        LOGGER.info("[ GET ] : getSummonerByName, region : {}, summonerId : {}", region, summonerId);
        Optional<Model<SummonerDto>> optionalSummonerDto = summonerService.get(new ItemId(region, summonerId));
        if (optionalSummonerDto.isPresent()) {
            return optionalSummonerDto.get().getItem();
        }
        return null;
    }
}
