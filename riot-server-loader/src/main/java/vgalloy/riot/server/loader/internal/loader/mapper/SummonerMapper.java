package vgalloy.riot.server.loader.internal.loader.mapper;

import java.util.Objects;

import vgalloy.riot.api.api.dto.mach.Player;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;

/**
 * @author Vincent Galloy - 19/12/16
 *         Created by Vincent Galloy on 19/12/16.
 */
public final class SummonerMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private SummonerMapper() {
        throw new AssertionError();
    }

    /**
     * Map {@link Player} into {@link SummonerDto}. This is a partial mapping.
     *
     * @param player the player
     * @return the summoner dto
     */
    public static SummonerDto map(Player player) {
        Objects.requireNonNull(player);
        SummonerDto result = new SummonerDto();
        result.setName(player.getSummonerName());
        result.setId(player.getSummonerId());
        result.setProfileIconId(player.getProfileIcon());
        return result;
    }
}
