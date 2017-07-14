package com.vgalloy.riot.server.loader.internal.loader;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 01/12/16.
 *
 * @author Vincent Galloy
 */
public interface SummonerLoader {

    /**
     * Load the summoner, his ranked stats and all his recent games and save it.
     *
     * @param region     the region of the summoner
     * @param summonerId the summoner id
     */
    void loadSummonerById(Region region, Long summonerId);

    /**
     * Load and save summoner by name.
     *
     * @param region       the region of the summoner
     * @param summonerName the summoner name
     */
    void loadSummonerByName(Region region, String summonerName);
}
