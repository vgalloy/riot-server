package vgalloy.riot.server.webservice.internal.mapper.summonerid;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;

/**
 * Created by Vincent Galloy on 28/12/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerIdMapperTest {

    private final SummonerIdMapper summonerIdMapper = new SummonerIdMapper();

    @Test
    public void simpleSerializationDeserialization() {
        // GIVEN
        SummonerId summonerId = new SummonerId(Region.EUW, 2L);

        // WHEN
        SummonerId result = summonerIdMapper.unmap(summonerIdMapper.map(summonerId));

        // THEN
        Assert.assertEquals(summonerId, result);
    }
}