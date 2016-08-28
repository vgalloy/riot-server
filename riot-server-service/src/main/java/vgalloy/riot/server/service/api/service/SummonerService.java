package vgalloy.riot.server.service.api.service;

import java.util.List;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.service.api.model.LastGame;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/06/16.
 */
public interface SummonerService extends CommonService<SummonerDto> {

    /**
     * Get the last games of a summoner.
     *
     * @param region     the region
     * @param summonerId the summoner id
     * @return the last games
     */
    List<LastGame> getLastGames(Region region, long summonerId);
}
