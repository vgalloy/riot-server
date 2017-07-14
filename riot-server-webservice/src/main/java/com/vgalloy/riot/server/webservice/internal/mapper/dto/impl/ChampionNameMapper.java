package com.vgalloy.riot.server.webservice.internal.mapper.dto.impl;

import org.springframework.stereotype.Component;

import com.vgalloy.riot.server.dao.api.entity.ChampionName;
import com.vgalloy.riot.server.webservice.api.dto.impl.ChampionNameDto;
import com.vgalloy.riot.server.webservice.internal.mapper.dto.DtoMapper;

/**
 * Created by Vincent Galloy on 01/05/17.
 *
 * @author Vincent Galloy
 */
@Component
final class ChampionNameMapper implements DtoMapper<ChampionName, ChampionNameDto> {

    @Override
    public ChampionNameDto map(ChampionName championName) {
        ChampionNameDto championNameDto = new ChampionNameDto();
        championNameDto.setChampionId(championName.getCommonDpoId().getId());
        championNameDto.setChampionName(championName.getChampionName());
        championNameDto.setRegion(championName.getCommonDpoId().getRegion());
        return championNameDto;
    }

    @Override
    public ChampionName unmap(ChampionNameDto championNameDto) {
        throw new UnsupportedOperationException();
    }
}
