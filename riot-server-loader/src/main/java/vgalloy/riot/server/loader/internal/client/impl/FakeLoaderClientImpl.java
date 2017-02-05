package vgalloy.riot.server.loader.internal.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.api.service.LoaderClient;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 03/12/16.
 */
public class FakeLoaderClientImpl implements LoaderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeLoaderClientImpl.class);

    @Override
    public int getItemInQueue(Region region) {
        return 0;
    }

    @Override
    public void loadAsyncItemById(Region region, Integer itemId) {
        LOGGER.warn("Enable to connect to the broker. You are using a fake Loader");
    }

    @Override
    public void loadAsyncSummonerById(Region region, Long summonerId) {
        LOGGER.warn("Enable to connect to the broker. You are using a fake Loader");
    }

    @Override
    public void loadAsyncSummonerByName(Region region, String summonerName) {
        LOGGER.warn("Enable to connect to the broker. You are using a fake Loader");
    }

    @Override
    public void loadChampionById(Region region, Long championId) {
        LOGGER.warn("Enable to connect to the broker. You are using a fake Loader");
    }
}
