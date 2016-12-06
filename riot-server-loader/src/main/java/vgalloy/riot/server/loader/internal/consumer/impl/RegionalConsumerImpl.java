package vgalloy.riot.server.loader.internal.consumer.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.loader.internal.consumer.RegionalConsumer;
import vgalloy.riot.server.loader.internal.helper.RegionPrinter;
import vgalloy.riot.server.loader.internal.loader.ItemLoader;
import vgalloy.riot.server.loader.internal.loader.SummonerLoader;
import vgalloy.riot.server.loader.internal.loader.mapper.LoadingMessageBuilder;
import vgalloy.riot.server.loader.internal.loader.message.LoadingMessage;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public class RegionalConsumerImpl implements RegionalConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionalConsumerImpl.class);

    private final Region region;
    private final SummonerLoader summonerLoader;
    private final ItemLoader itemLoader;

    /**
     * Constructor.
     *
     * @param region         the region
     * @param summonerLoader the regional summoner loader
     * @param itemLoader     the item loader
     */
    public RegionalConsumerImpl(Region region, SummonerLoader summonerLoader, ItemLoader itemLoader) {
        this.region = Objects.requireNonNull(region);
        this.summonerLoader = Objects.requireNonNull(summonerLoader);
        this.itemLoader = Objects.requireNonNull(itemLoader);
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
        }
    }
}