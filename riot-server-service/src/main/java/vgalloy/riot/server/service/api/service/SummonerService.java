package vgalloy.riot.server.service.api.service;

import java.time.LocalDateTime;
import java.util.List;

import vgalloy.riot.server.dao.api.entity.GetSummonersQuery;
import vgalloy.riot.server.service.api.model.summoner.GameSummary;
import vgalloy.riot.server.service.api.model.summoner.Summoner;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;

/**
 * Created by Vincent Galloy on 23/06/16.
 *
 * @author Vincent Galloy
 */
public interface SummonerService {

    /**
     * Get the summoner by id.
     *
     * @param summonerId the summoner id
     * @return the summoner
     */
    ResourceWrapper<Summoner> get(SummonerId summonerId);

    /**
     * Get the games of a summoner during the period : [from, to[. The games are sorted by date (ascending).
     *
     * @param summonerId the summoner id
     * @param from       the search start date
     * @param to         the search end date
     * @return the last games
     */
    List<GameSummary> getLastGames(SummonerId summonerId, LocalDateTime from, LocalDateTime to);

    /**
     * Get the summoner by name and region.
     *
     * @param getSummonersQuery the param for the query
     * @return the summoner'id matching with request
     */
    List<Summoner> getSummoners(GetSummonersQuery getSummonersQuery);
}
