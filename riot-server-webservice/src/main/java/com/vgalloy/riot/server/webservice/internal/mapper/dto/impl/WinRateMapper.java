package com.vgalloy.riot.server.webservice.internal.mapper.dto.impl;

import org.springframework.stereotype.Component;

import com.vgalloy.riot.server.dao.api.entity.WinRate;
import com.vgalloy.riot.server.webservice.api.dto.impl.WinRateDto;
import com.vgalloy.riot.server.webservice.internal.mapper.dto.DtoMapper;

/**
 * Created by Vincent Galloy on 01/05/17.
 *
 * @author Vincent Galloy
 */
@Component
final class WinRateMapper implements DtoMapper<WinRate, WinRateDto> {

    @Override
    public WinRateDto map(WinRate winRate) {
        WinRateDto winRateDto = new WinRateDto();
        winRateDto.setWin(winRate.getWin());
        winRateDto.setLose(winRate.getLose());
        return winRateDto;
    }

    @Override
    public WinRate unmap(WinRateDto winRateDto) {
        throw new UnsupportedOperationException();
    }
}
