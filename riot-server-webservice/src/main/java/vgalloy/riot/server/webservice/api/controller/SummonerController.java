package vgalloy.riot.server.webservice.api.controller;

import java.util.List;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.summoner.GameSummary;
import vgalloy.riot.server.service.api.model.summoner.RankedStats;
import vgalloy.riot.server.service.api.model.summoner.Summoner;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * Created by Vincent Galloy on 20/06/16.
 *
 * @author Vincent Galloy
 */
public interface SummonerController {

    /**
     * Get the summoner with criteria.
     *
     * @param regions       the region (default ALL)
     * @param summonerNames the summoner name (default ALL)
     * @param offset        the request offset (default 0)
     * @param limit         the max limit (default 10)
     * @return the last games
     */
    List<Summoner> getSummoners(List<Region> regions, List<String> summonerNames, Integer offset, Integer limit);

    /**
     * Get the summoner by id.
     *
     * @param summonerId the summoner id
     * @return the last games
     */
    Summoner getSummonerById(SummonerId summonerId);

    /**
     * Get the ranked stats.
     *
     * @param summonerId the summoner id
     * @return the ranked stats
     */
    RankedStats getRankedStat(SummonerId summonerId);

    /**
     * Get the last games of a summoner during the period [from, to]. Game sorted by date.
     *
     * @param summonerId the summoner id
     * @param fromDay    the start search date in day
     * @param toDay      the end search date in day
     * @return the last games
     */
    List<GameSummary> getLastGames(SummonerId summonerId, Long fromDay, Long toDay);
}
