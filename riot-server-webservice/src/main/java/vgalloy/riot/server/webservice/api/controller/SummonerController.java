package vgalloy.riot.server.webservice.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.service.api.model.LastGame;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.model.Position;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.RankedStatsService;
import vgalloy.riot.server.service.api.service.SummonerService;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 20/06/16.
 */
@RestController
public class SummonerController {

    @Autowired
    private QueryService queryService;
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
        Optional<Model<RankedStatsDto>> rankedStatsEntity = rankedStatsService.get(region, summonerId);
        if (rankedStatsEntity.isPresent()) {
            return rankedStatsEntity.get();
        }
        return null;
    }

    /**
     * Get the position of a summoner with a given champ.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @param championId the champion id
     * @return the position as a list. Each list represent a game
     */
    @RequestMapping(value = "/summoner/{region}/{summonerId}/position/{championId}", method = RequestMethod.GET)
    public List<List<Position>> getPosition(@PathVariable Region region, @PathVariable Integer summonerId, @PathVariable Integer championId) {
        return queryService.getPosition(summonerId, championId);
    }

    /**
     * Get the last games of a summoner.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @param limit      the limit of result to fetch (default 10)
     * @return the last games
     */
    @RequestMapping(value = "/summoner/{region}/{summonerId}/lastGames", method = RequestMethod.GET)
    public List<LastGame> getLastGames(@PathVariable Region region, @PathVariable Long summonerId, @RequestParam(value = "limit", required = false) Integer limit) {
        return summonerService.getLastGames(region, summonerId, Optional.ofNullable(limit));
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
        Optional<Model<SummonerDto>> optionalSummonerDto = summonerService.get(region, summonerId);
        if (optionalSummonerDto.isPresent()) {
            return optionalSummonerDto.get().getItem();
        }
        return null;
    }
}
