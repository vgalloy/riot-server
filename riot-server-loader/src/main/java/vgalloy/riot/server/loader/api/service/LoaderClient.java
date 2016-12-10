package vgalloy.riot.server.loader.api.service;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public interface LoaderClient {

    /**
     * Get the number of item in the queue.
     *
     * @param region the region
     * @return number of item in the queue.
     */
    int getItemInQueue(Region region);

    /**
     * Load the item and save it.
     *
     * @param region the region of the item
     * @param itemId the item id
     */
    void loadAsyncItemById(Region region, Integer itemId);

    /**
     * Load the summoner, his ranked stats and all his recent games and save it.
     *
     * @param region     the region of the summoner
     * @param summonerId the summoner id
     */
    void loadAsyncSummonerById(Region region, Long summonerId);

    /**
     * Load and save summoner by name.
     *
     * @param region       the region of the summoner
     * @param summonerName the summoner name
     */
    void loadAsyncSummonerByName(Region region, String summonerName);

    /**
     * Load and save champion by id.
     *
     * @param region     the region of the summoner
     * @param championId the champion id
     */
    void loadChampionById(Region region, Long championId);
}
