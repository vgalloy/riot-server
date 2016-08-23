package vgalloy.riot.server.webservice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.stats.dto.RankedStatsDto;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.model.Position;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.RankedStatsService;

import java.util.List;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 20/06/16.
 */
@RestController
public class SummonerController {

    @Autowired
    private QueryService queryService;
    @Autowired
    private RankedStatsService rankedStatsService;

    /**
     * Get the ranked stats.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @return the ranked stats
     */
    @RequestMapping(value = "{region}/summoner/{summonerId}", method = RequestMethod.GET)
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
    @RequestMapping(value = "{region}/summoner/{summonerId}/{championId}", method = RequestMethod.GET)
    public List<List<Position>> getPosition(@PathVariable Region region, @PathVariable Integer summonerId, @PathVariable Integer championId) {
        return queryService.getPosition(summonerId, championId);
    }
}
