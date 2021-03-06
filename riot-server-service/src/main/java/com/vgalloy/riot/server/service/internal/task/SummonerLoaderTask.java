package com.vgalloy.riot.server.service.internal.task;

import java.util.Objects;
import java.util.Optional;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;

import com.vgalloy.riot.server.dao.api.dao.SummonerDao;
import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.loader.api.service.LoaderClient;

/**
 * Created by Vincent Galloy on 06/12/16.
 *
 * @author Vincent Galloy
 */
public class SummonerLoaderTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerLoaderTask.class);

    private final SummonerDao summonerDao;
    private final LoaderClient loaderClient;

    /**
     * Constructor.
     *
     * @param summonerDao  the summoner dao
     * @param loaderClient the loader client
     */
    public SummonerLoaderTask(SummonerDao summonerDao, LoaderClient loaderClient) {
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.loaderClient = Objects.requireNonNull(loaderClient);
    }

    @Override
    public void run() {
        try {
            for (Region region : Region.values()) {
                int queueLength = loaderClient.getItemInQueue(region);
                LOGGER.info("Queue {} has {} message(s)", region, queueLength);
                if (queueLength == 0) {
                    addSummoner(region);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    /**
     * Add a new random summoner to loading queue.
     *
     * @param region the region of the summoner
     */
    private void addSummoner(Region region) {
        Optional<Long> summonerId = getRandomSummonerId(region);
        LOGGER.info("Load summoner : {} for queue : {}", summonerId, region);
        summonerId.ifPresent(id -> loaderClient.loadAsyncSummonerById(region, id));
    }

    /**
     * Obtains a random summoner Id.
     *
     * @param region the region of the summoner
     * @return an optional with the id
     */
    private Optional<Long> getRandomSummonerId(Region region) {
        Optional<Entity<SummonerDto, DpoId>> optionalSummonerDto = summonerDao.getRandom(region);
        if (optionalSummonerDto.isPresent() && optionalSummonerDto.get().getItem().isPresent()) {
            SummonerDto summonerDto = optionalSummonerDto.get().getItem().get();
            return Optional.of(summonerDto.getId());
        }
        return Optional.empty();
    }
}
