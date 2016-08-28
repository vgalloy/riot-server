package vgalloy.riot.server.service.internal.loader.mapper;

import vgalloy.riot.api.api.dto.mach.Player;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class SummonerDtoMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private SummonerDtoMapper() {
        throw new AssertionError();
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
