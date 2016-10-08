package vgalloy.riot.server.service.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.service.api.model.LastGame;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/06/16.
 */
public interface SummonerService extends CommonService<SummonerDto> {

    /**
     * Get the games of a summoner during the period : [from, to]. The games are sorted by date.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @param from       the search start date
     * @param to         the search end date
     * @return the last games
     */
    List<LastGame> getLastGames(Region region, long summonerId, LocalDate from, LocalDate to);

    /**
     * Get the summoner by name and region.
     *
     * @param region       the region
     * @param summonerName the summoner name
     * @return the summoner
     */
    Optional<SummonerDto> getSummonerByName(Region region, String summonerName);
}
