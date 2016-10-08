package vgalloy.riot.server.service.internal.loader.consumer.impl;

import java.util.Objects;
import java.util.function.Consumer;

import vgalloy.riot.api.api.constant.Region;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public interface RegionalSummonerLoaderConsumer extends Consumer<Long> {

    /**
     * Get the queue name.
     *
     * @param region the region
     * @return the queue name
     */
    static String getQueueName(Region region) {
        return "LOADER_" + Objects.requireNonNull(region);
    }

    /**
     * Load the summoner, his ranked stats and all his recent games and save it.
     *
     * @param summonerId the summoner id
     */
    void loaderSummoner(Long summonerId);
}
