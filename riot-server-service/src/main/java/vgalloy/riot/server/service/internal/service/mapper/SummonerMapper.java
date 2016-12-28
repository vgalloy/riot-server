package vgalloy.riot.server.service.internal.service.mapper;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.service.api.model.summoner.Summoner;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * @author Vincent Galloy - 28/12/16
 *         Created by Vincent Galloy on 28/12/16.
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
     * Map the {@link SummonerDto} into {@link Summoner}.
     *
     * @param region      the region
     * @param summonerDto the summoner dto
     * @return the summoner
     */
    public static Summoner map(Region region, SummonerDto summonerDto) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(summonerDto);

        return new Summoner(new SummonerId(region, summonerDto.getId()), summonerDto.getName(), summonerDto.getSummonerLevel(), summonerDto.getProfileIconId());
    }
}
