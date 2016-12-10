package vgalloy.riot.server.loader.internal.client.impl;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.api.service.LoaderClient;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 03/12/16.
 */
public class FakeLoaderClientImpl implements LoaderClient {

    @Override
    public int getItemInQueue(Region region) {
        return 0;
    }

    @Override
    public void loadAsyncItemById(Region region, Integer itemId) {

    }

    @Override
    public void loadAsyncSummonerById(Region region, Long summonerId) {

    }

    @Override
    public void loadAsyncSummonerByName(Region region, String summonerName) {

    }

    @Override
    public void loadChampionById(Region region, Long championId) {

    }
}
