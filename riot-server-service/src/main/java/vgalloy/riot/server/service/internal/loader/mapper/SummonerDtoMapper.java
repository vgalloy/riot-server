package vgalloy.riot.server.service.internal.loader.mapper;

import vgalloy.riot.api.rest.request.mach.dto.Player;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class SummonerDtoMapper {

    /**
     * Constructor.
     */
    private SummonerDtoMapper() {
    }

    /**
     * Convert a Player in SummonerDto.
     *
     * @param player the player
     * @return the summonerDto with missing level and updateDate
     */
    public static SummonerDto map(Player player) {
        SummonerDto summonerDto = new SummonerDto();
        summonerDto.setId(player.getSummonerId());
        summonerDto.setName(player.getSummonerName());
        summonerDto.setProfileIconId(player.getProfileIcon());
        return summonerDto;
    }
}
