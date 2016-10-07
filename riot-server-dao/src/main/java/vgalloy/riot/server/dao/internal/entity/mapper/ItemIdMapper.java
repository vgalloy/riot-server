package vgalloy.riot.server.dao.internal.entity.mapper;

import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public final class ItemIdMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ItemIdMapper() {
        throw new AssertionError();
    }

    /**
     * Convert item id into String.
     *
     * @param itemId the item id
     * @return the normalized string
     */
    public static String toNormalizeString(ItemId itemId) {
        Objects.requireNonNull(itemId);
        return itemId.getRegion() + " " + itemId.getId();
    }

    /**
     * Convert the string into itemId.
     *
     * @param string the string
     * @return the item id
     */
    public static ItemId fromNormalize(String string) {
        Objects.requireNonNull(string);
        String[] divided = string.split(" ");
        Region region = Region.valueOf(divided[0]);
        Long id = Long.valueOf(divided[1]);
        return new ItemId(region, id);
    }
}
