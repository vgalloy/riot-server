package vgalloy.riot.server.loader.internal.loader;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/12/16.
 */
public interface ChampionLoader {

    /**
     * Load the champion and save it.
     *
     * @param region     the region of the champion
     * @param championId the champion id
     */
    void loadChampionById(Region region, Long championId);
}
