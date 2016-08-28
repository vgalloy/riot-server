package vgalloy.riot.server.service.internal.loader.impl.rankedstats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 *         Need : Summoner
 *         Load : RankedStats
 */
public class RankedStatsLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankedStatsLoader.class);

    private final Region region;
    private final CommonDao<SummonerDto> summonerDao;
    private final CommonDao<RankedStatsDto> rankedStatsDao;

    /**
     * Constructor.
     *
     * @param riotApi        the riot api
     * @param executor       the executor
     * @param region         the region
     * @param summonerDao    the summoner dao
     * @param rankedStatsDao the ranked stats dao
     */
    public RankedStatsLoader(RiotApi riotApi, Executor executor, Region region, CommonDao<SummonerDto> summonerDao, CommonDao<RankedStatsDto> rankedStatsDao) {
        super(riotApi, executor);
        this.region = Objects.requireNonNull(region);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.rankedStatsDao = Objects.requireNonNull(rankedStatsDao);
    }

    @Override
    public void execute() {
        while (true) {
            Entity<SummonerDto> summonerEntity = getRandomSummoner();
            long summonerId = summonerEntity.getItem().getId();
            if (notLoaded(summonerId)) {
                RankedStatsDto rankedStatsDto = load(summonerId);
                rankedStatsDao.save(region, summonerId, rankedStatsDto);
            }
        }
    }

    /**
     * Return a random SummonerDto.
     *
     * @return a random SummonerDto
     */
    private Entity<SummonerDto> getRandomSummoner() {
        long sleepingTime = 0;
        while (true) {
            Optional<Entity<SummonerDto>> summonerEntity = summonerDao.getRandom(region);
            if (summonerEntity.isPresent()) {
                return summonerEntity.get();
            } else {
                LOGGER.warn("{} : No summoner found", RegionPrinter.getRegion(region));
                try {
                    sleepingTime += 1_000;
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }

    /**
     * Is the summoner not already in data base.
     *
     * @param summonerId the summoner id
     * @return true if the summoner is not in the database
     */

    private boolean notLoaded(long summonerId) {
        return !rankedStatsDao.get(region, summonerId).isPresent();
    }

    /**
     * Load the ranked stats for a given summoner.
     *
     * @param summonerId the summoner id
     * @return the RankedStatsDto
     */
    private RankedStatsDto load(long summonerId) {
        loaderInformation.addRequest();
        loaderInformation.addRankedStatsRequest();
        Query<RankedStatsDto> query = riotApi.getRankedStats(summonerId).region(region);
        RankedStatsDto rankedStatsDto = executor.execute(query, region, 1);
        if (rankedStatsDto == null) {
            rankedStatsDto = new RankedStatsDto();
            rankedStatsDto.setSummonerId(summonerId);
        }
        LOGGER.info("{} : {} - {} ", RegionPrinter.getRegion(region), summonerId, loaderInformation.printInformation());
        return rankedStatsDto;
    }
}
