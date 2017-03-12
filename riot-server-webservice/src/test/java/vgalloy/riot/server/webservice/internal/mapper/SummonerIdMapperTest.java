package vgalloy.riot.server.webservice.internal.mapper;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.webservice.internal.mapper.summonerid.SummonerIdMapper;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerIdMapperTest {

    @Test
    public void simpleSerializationDeserialization() {
        // GIVEN
        SummonerId summonerId = new SummonerId(Region.EUW, 2L);

        // WHEN
        SummonerId result = SummonerIdMapper.map(SummonerIdMapper.map(summonerId));

        // THEN
        Assert.assertEquals(summonerId, result);
    }
}