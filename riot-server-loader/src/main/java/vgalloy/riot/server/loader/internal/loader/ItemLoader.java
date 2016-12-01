package vgalloy.riot.server.loader.internal.loader;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 01/12/16
 *         Created by Vincent Galloy on 01/12/16.
 */
public interface ItemLoader {

    /**
     * Load the item and save it.
     *
     * @param region the region of the item
     * @param itemId the item id
     */
    void loadItemById(Region region, Integer itemId);
}
