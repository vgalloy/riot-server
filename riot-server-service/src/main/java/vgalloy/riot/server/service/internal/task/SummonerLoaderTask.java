package vgalloy.riot.server.service.internal.task;

import java.util.Objects;
import java.util.Optional;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.loader.api.service.LoaderClient;

/**
 * @author Vincent Galloy - 06/12/16
 *         Created by Vincent Galloy on 06/12/16.
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
        for (Region region : Region.values()) {
            int queueLength = loaderClient.getItemInQueue(region);
            LOGGER.info("Queue {} has {} message(s)", region, queueLength);
            if (queueLength == 0) {
                addSummoner(region);
            }
        }
    }

    /**
     * Add a new random summoner to loading queue.
     *
     * @param region the region of the summoner
     */
    private void addSummoner(Region region) {
        Optional<Long> summonerId = getRandomSummonerId(region);
        LOGGER.info("Load summoner : {}", summonerId);
        summonerId.ifPresent(id -> loaderClient.loadAsyncSummonerById(region, id));
    }

    /**
     * Obtains a random summoner Id.
     *
     * @param region the region of the summoner
     * @return an optional with the id
     */
    private Optional<Long> getRandomSummonerId(Region region) {
        Optional<Entity<SummonerDto, ItemId>> optionalSummonerDto = summonerDao.getRandom(region);
        if (optionalSummonerDto.isPresent() && optionalSummonerDto.get().getItem().isPresent()) {
            SummonerDto summonerDto = optionalSummonerDto.get().getItem().get();
            return Optional.of(summonerDto.getId());
        }
        return Optional.empty();
    }
}
