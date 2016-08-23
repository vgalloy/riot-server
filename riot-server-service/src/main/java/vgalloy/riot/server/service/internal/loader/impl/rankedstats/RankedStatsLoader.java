package vgalloy.riot.server.service.internal.loader.impl.rankedstats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.stats.dto.RankedStatsDto;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.api.service.query.Query;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.model.LoaderInformation;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
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
    private final LoaderInformation loaderInformation;

    @Autowired
    private CommonDao<SummonerDto> summonerDao;
    @Autowired
    private CommonDao<RankedStatsDto> rankedStatsDao;

    /**
     * Constructor.
     *
     * @param region the region
     */
    public RankedStatsLoader(Region region) {
        this.region = Objects.requireNonNull(region, "region can not be null");
        loaderInformation = new LoaderInformation();
    }

    @Override
    public void run() {
        while (true) {
            Optional<Entity<SummonerDto>> summonerEntity = summonerDao.getRandom(region);
            if (summonerEntity.isPresent()) {
                long summonerId = summonerEntity.get().getItem().getId();
                if (notLoaded(summonerId)) {
                    RankedStatsDto rankedStatsDto = load(summonerId);
                    rankedStatsDao.save(region, summonerId, rankedStatsDto);
                }
            } else {
                LOGGER.warn("{} : No summoner find", RegionPrinter.getRegion(region));
                try {
                    Thread.sleep(1_000);
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
        return rankedStatsDao.get(region, summonerId) == null;
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

    @Override
    public LoaderInformation getLoaderInformation() {
        return loaderInformation;
    }
}
