package com.vgalloy.riot.server.webservice.internal.mapper.summonerid;

import org.springframework.stereotype.Component;

import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;
import com.vgalloy.riot.server.webservice.internal.mapper.AbstractSerializer;
import com.vgalloy.riot.server.webservice.internal.mapper.Mapper;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
@Component
final class SummonerIdConverter extends AbstractSerializer<SummonerId> {

    private static final long serialVersionUID = 4299902797629912725L;

    /**
     * Constructor.
     *
     * @param mapper the corresponding mapper
     */
    SummonerIdConverter(Mapper<SummonerId, String> mapper) {
        super(mapper);
    }
}
