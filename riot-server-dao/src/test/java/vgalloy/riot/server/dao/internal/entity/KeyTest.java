package vgalloy.riot.server.dao.internal.entity;

import org.junit.Test;

import vgalloy.riot.api.api.constant.Region;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vincent Galloy on 25/08/16.
 *
 * @author Vincent Galloy
 */
public final class KeyTest {

    @Test
    public void testSerializationAndDeserialization() {
        // GIVEN
        Key key = new Key(Region.BR, 10L);

        // WHEN
        String keyAsString = key.normalizeString();

        // THEN
        assertEquals(key, Key.fromNormalizedString(keyAsString));
    }
}