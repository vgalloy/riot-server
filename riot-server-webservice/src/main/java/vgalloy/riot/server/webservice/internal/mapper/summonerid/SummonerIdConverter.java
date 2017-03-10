package vgalloy.riot.server.webservice.internal.mapper.summonerid;

import org.springframework.stereotype.Component;

import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.webservice.internal.mapper.AbstractSerializer;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
@Component
public final class SummonerIdConverter extends AbstractSerializer<SummonerId> {

    private static final long serialVersionUID = 4299902797629912725L;

    @Override
    protected SummonerId map(String string) {
        return SummonerIdMapper.map(string);
    }

    @Override
    protected String unmap(SummonerId summonerId) {
        return SummonerIdMapper.map(summonerId);
    }
}
