package vgalloy.riot.server.service.internal.loader.client;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public interface SummonerLoaderClient {

    /**
     * Load the summoner, his ranked stats and all his recent games and save it.
     *
     * @param region     the region of the summoner
     * @param summonerId the summoner id
     */
    void loaderSummoner(Region region, Long summonerId);
}
