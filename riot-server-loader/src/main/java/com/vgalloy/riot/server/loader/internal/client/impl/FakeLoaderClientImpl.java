package com.vgalloy.riot.server.loader.internal.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.loader.api.service.LoaderClient;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public final class FakeLoaderClientImpl implements LoaderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeLoaderClientImpl.class);
    private static final String DEFAULT_WARNING_MESSAGE = "Enable to connect to the broker. You are using a fake Loader";

    @Override
    public int getItemInQueue(Region region) {
        LOGGER.warn(DEFAULT_WARNING_MESSAGE);
        return 0;
    }

    @Override
    public void loadAsyncItemById(Region region, Integer itemId) {
        LOGGER.warn(DEFAULT_WARNING_MESSAGE);
    }

    @Override
    public void loadAsyncSummonerById(Region region, Long summonerId) {
        LOGGER.warn(DEFAULT_WARNING_MESSAGE);
    }

    @Override
    public void loadAsyncSummonerByName(Region region, String summonerName) {
        LOGGER.warn(DEFAULT_WARNING_MESSAGE);
    }

    @Override
    public void loadChampionById(Region region, Long championId) {
        LOGGER.warn(DEFAULT_WARNING_MESSAGE);
    }
}
