package com.vgalloy.riot.server.webservice.internal.mapper.summonerid;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;
import com.vgalloy.riot.server.service.api.service.exception.ServiceException;
import com.vgalloy.riot.server.webservice.internal.mapper.Mapper;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
@Component
final class SummonerIdMapper implements Mapper<SummonerId, String> {

    @Override
    public String map(SummonerId summonerId) {
        return summonerId.getRegion().name() + summonerId.getId();
    }

    @Override
    public SummonerId unmap(String string) {
        Objects.requireNonNull(string);
        Pattern pattern = Pattern.compile("([A-Z]*)([0-9]*)");
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find()) {
            throw new ServiceException("Can not convert " + string + " into SummonerId");
        }
        Region region = Region.valueOf(matcher.group(1));
        Long id = Long.valueOf(matcher.group(2));
        return new SummonerId(region, id);
    }
}
