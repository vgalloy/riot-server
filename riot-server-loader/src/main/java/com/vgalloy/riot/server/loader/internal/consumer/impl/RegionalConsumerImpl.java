package com.vgalloy.riot.server.loader.internal.consumer.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.loader.internal.consumer.RegionalConsumer;
import com.vgalloy.riot.server.loader.internal.helper.RegionPrinter;
import com.vgalloy.riot.server.loader.internal.loader.ChampionLoader;
import com.vgalloy.riot.server.loader.internal.loader.ItemLoader;
import com.vgalloy.riot.server.loader.internal.loader.SummonerLoader;
import com.vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageBuilder;
import com.vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * Created by Vincent Galloy on 10/10/16.
 *
 * @author Vincent Galloy
 */
public final class RegionalConsumerImpl implements RegionalConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionalConsumerImpl.class);

    private final Region region;
    private final SummonerLoader summonerLoader;
    private final ItemLoader itemLoader;
    private final ChampionLoader championLoader;

    /**
     * Constructor.
     *
     * @param region         the region
     * @param summonerLoader the regional summoner loader
     * @param itemLoader     the item loader
     * @param championLoader the champion loader
     */
    public RegionalConsumerImpl(Region region, SummonerLoader summonerLoader, ItemLoader itemLoader, ChampionLoader championLoader) {
        this.region = Objects.requireNonNull(region);
        this.summonerLoader = Objects.requireNonNull(summonerLoader);
        this.itemLoader = Objects.requireNonNull(itemLoader);
        this.championLoader = Objects.requireNonNull(championLoader);
    }

    @Override
    public void accept(LoadingMessage loadingMessage) {
        LOGGER.info("{} load : {}", RegionPrinter.getRegion(region), loadingMessage);
        switch (loadingMessage.getLoaderType()) {
            case SUMMONER_BY_ID:
                summonerLoader.loadSummonerById(region, LoadingMessageBuilder.summonerId().extract(loadingMessage));
                break;
            case SUMMONER_BY_NAME:
                summonerLoader.loadSummonerByName(region, LoadingMessageBuilder.summonerName().extract(loadingMessage));
                break;
            case ITEM_BY_ID:
                itemLoader.loadItemById(region, LoadingMessageBuilder.itemId().extract(loadingMessage));
                break;
            case CHAMPION_BY_ID:
                championLoader.loadChampionById(region, LoadingMessageBuilder.championId().extract(loadingMessage));
                break;
            default:
                LOGGER.error("Unknown message type {}", loadingMessage.getLoaderType());
                break;
        }
    }
}
