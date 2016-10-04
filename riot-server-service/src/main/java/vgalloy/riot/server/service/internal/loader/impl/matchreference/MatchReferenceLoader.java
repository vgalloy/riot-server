package vgalloy.riot.server.service.internal.loader.impl.matchreference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.matchlist.MatchList;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.ItemWrapper;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.LoaderHelper;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 *         Need : Summoner
 *         Load : MatchReference
 */
public class MatchReferenceLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchReferenceLoader.class);

    private final Region region;
    private final SummonerDao summonerDao;
    private final MatchReferenceDao matchReferenceDao;

    /**
     * Constructor.
     *
     * @param riotApi           the riot api
     * @param executor          the executor
     * @param region            the region
     * @param summonerDao       the summoner dao
     * @param matchReferenceDao the match reference dao
     */
    public MatchReferenceLoader(RiotApi riotApi, Executor executor, Region region, SummonerDao summonerDao, MatchReferenceDao matchReferenceDao) {
        super(riotApi, executor);
        this.region = Objects.requireNonNull(region);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchReferenceDao = Objects.requireNonNull(matchReferenceDao);
    }

    @Override
    public void execute() {
        while (true) {
            long summonerId = LoaderHelper.getRandomSummonerId(summonerDao, region, LOGGER);
            List<MatchReference> matchReferences = load(summonerId);
            matchReferences.forEach(e -> matchReferenceDao.save(new ItemWrapper<>(region, e.getMatchId(), e)));
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
        Query<MatchList> query = riotApi.getMatchListBySummonerId(summonerId).region(region);
        MatchList matchList = executor.execute(query, region, 1);
        if (matchList == null || matchList.getMatches() == null) {
            return new ArrayList<>();
        }
        LOGGER.info("{} : matchReference {}", RegionPrinter.getRegion(region), summonerId);
        return matchList.getMatches();
    }
}
