package vgalloy.riot.server.dao.internal.entity.mapper;

import org.junit.Assert;
import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;

/**
 * Created by Vincent Galloy on 24/03/17.
 *
 * @author Vincent Galloy
 */
public final class DpoIdMapperTest {

    @Test
    public void testSerializationAndDeserialization() {
        // GIVEN
        DpoId dpoId = new CommonDpoId(Region.BR, 10L);

        // WHEN
        DpoId result = DpoIdMapper.fromNormalize(DpoIdMapper.toNormalizedString(dpoId));

        // THEN
        Assert.assertEquals(dpoId, result);
    }
}