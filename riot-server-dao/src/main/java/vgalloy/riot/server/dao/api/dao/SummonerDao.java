package vgalloy.riot.server.dao.api.dao;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;

import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/08/16.
 */
public interface SummonerDao extends CommonDao<SummonerDto> {

    /**
     * Get the summoner by name and region.
     *
     * @param region       the region
     * @param summonerName the summoner name
     * @return the summoner
     */
    Optional<SummonerDto> getSummonerByName(Region region, String summonerName);
}
