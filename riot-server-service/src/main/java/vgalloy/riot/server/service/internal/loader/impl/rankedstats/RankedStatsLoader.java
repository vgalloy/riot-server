package vgalloy.riot.server.service.internal.loader.impl.rankedstats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.ItemWrapper;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.LoaderHelper;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 *         Need : Summoner
 *         Load : RankedStats
 */
public class RankedStatsLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankedStatsLoader.class);

    private final Region region;
    private final SummonerDao summonerDao;
    private final RankedStatsDao rankedStatsDao;

    /**
     * Constructor.
     *
     * @param riotApi        the riot api
     * @param executor       the executor
     * @param region         the region
     * @param summonerDao    the summoner dao
     * @param rankedStatsDao the ranked stats dao
     */
    public RankedStatsLoader(RiotApi riotApi, Executor executor, Region region, SummonerDao summonerDao, RankedStatsDao rankedStatsDao) {
        super(riotApi, executor);
        this.region = Objects.requireNonNull(region);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.rankedStatsDao = Objects.requireNonNull(rankedStatsDao);
    }

    @Override
    public void execute() {
        while (true) {
            long summonerId = LoaderHelper.getRandomSummonerId(summonerDao, region, LOGGER);
            if (notLoaded(summonerId)) {
                Optional<RankedStatsDto> rankedStatsDto = load(summonerId);
                if (rankedStatsDto.isPresent()) {
                    rankedStatsDao.save(new ItemWrapper<>(region, summonerId, rankedStatsDto.get()));
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
    private Optional<RankedStatsDto> load(long summonerId) {
        loaderInformation.addRequest();
        LOGGER.info("{} : rankedStats {}", RegionPrinter.getRegion(region), summonerId);
        Query<RankedStatsDto> query = riotApi.getRankedStats(summonerId).region(region);
        return Optional.ofNullable(executor.execute(query, region, 1));
    }
}
