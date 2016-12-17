package vgalloy.riot.server.dao.internal.entity.mapper;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public final class DpoIdMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private DpoIdMapper() {
        throw new AssertionError();
    }

    /**
     * Convert item id into String.
     *
     * @param dpoId the item id
     * @return the normalized string
     */
    public static String toNormalizeString(DpoId dpoId) {
        Objects.requireNonNull(dpoId);
        return dpoId.getRegion() + " " + dpoId.getId();
    }

    /**
     * Convert the string into itemId.
     *
     * @param string the string
     * @return the item id
     */
    public static DpoId fromNormalize(String string) {
        Objects.requireNonNull(string);
        String[] divided = string.split(" ");
        Region region = Region.valueOf(divided[0]);
        Long id = Long.valueOf(divided[1]);
        return new DpoId(region, id);
    }
}
