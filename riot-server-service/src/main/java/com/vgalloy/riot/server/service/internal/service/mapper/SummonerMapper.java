package com.vgalloy.riot.server.service.internal.service.mapper;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;

import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.service.api.model.summoner.Summoner;
import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
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

    /**
     * Map the {@link Entity} into {@link Summoner}.
     *
     * @param entity the entity
     * @return the summoner
     */
    public static Summoner map(Entity<SummonerDto, DpoId> entity) {
        if (entity.getItem().isPresent()) {
            return SummonerMapper.map(entity.getItemId().getRegion(), entity.getItem().get());
        } else {
            return new Summoner(new SummonerId(entity.getItemId().getRegion(), entity.getItemId().getId()), null, null, null);
        }
    }
}
