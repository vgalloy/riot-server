package vgalloy.riot.server.service.internal.loader.impl.matchreference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.matchlist.MatchList;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 *         Need : Summoner
 *         Load : MatchReference
 */
public class MatchReferenceLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchReferenceLoader.class);

    private final Region region;
    private final CommonDao<SummonerDto> summonerDao;
    private final CommonDao<MatchReference> matchReferenceDao;

    /**
     * Constructor.
     *
     * @param riotApi           the riot api
     * @param executor          the executor
     * @param region            the region
     * @param summonerDao       the summoner dao
     * @param matchReferenceDao the match reference dao
     */
    public MatchReferenceLoader(RiotApi riotApi, Executor executor, Region region, CommonDao<SummonerDto> summonerDao, CommonDao<MatchReference> matchReferenceDao) {
        super(riotApi, executor);
        this.region = Objects.requireNonNull(region);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchReferenceDao = Objects.requireNonNull(matchReferenceDao);
    }

    @Override
    public void execute() {
        while (true) {
            Entity<SummonerDto> summonerEntity = getRandomSummoner();
            long summonerId = summonerEntity.getItem().getId();
            List<MatchReference> matchReferences = load(summonerId);
            matchReferences.forEach(e -> matchReferenceDao.save(region, e.getMatchId(), e));
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
     * Load the ranked stats for a given summoner.
     *
     * @param summonerId the summoner id
     * @return the RankedStatsDto
     */
    private List<MatchReference> load(long summonerId) {
        loaderInformation.addRequest();
        loaderInformation.addRankedStatsRequest();
        Query<MatchList> query = riotApi.getMatchListBySummonerId(summonerId).region(region);
        MatchList matchList = executor.execute(query, region, 1);
        if (matchList == null || matchList.getMatches() == null) {
            return new ArrayList<>();
        }
        LOGGER.info("{} : {} - {} ", RegionPrinter.getRegion(region), summonerId, loaderInformation.printInformation());
        return matchList.getMatches();
    }
}
