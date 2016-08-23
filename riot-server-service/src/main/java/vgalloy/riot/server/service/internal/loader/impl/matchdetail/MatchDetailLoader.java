package vgalloy.riot.server.service.internal.loader.impl.matchdetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.api.rest.request.mach.dto.ParticipantIdentity;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchReference;
import vgalloy.riot.api.rest.request.summoner.dto.SummonerDto;
import vgalloy.riot.api.service.RiotApi;
import vgalloy.riot.api.service.query.Query;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;
import vgalloy.riot.server.service.internal.loader.mapper.SummonerDtoMapper;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 *         Need : MatchReference
 *         Load : MatchDetail && Summoner
 */
public class MatchDetailLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDetailLoader.class);

    private final Region region;
    private final CommonDao<SummonerDto> summonerDao;
    private final CommonDao<MatchDetail> matchDetailDao;
    private final CommonDao<MatchReference> matchReferenceDao;

    /**
     * Constructor.
     *
     * @param riotApi           the riot api
     * @param executor          the executor
     * @param region            the region
     * @param summonerDao       the summoner dao
     * @param matchDetailDao    the match detail dao
     * @param matchReferenceDao the match reference dao
     */
    public MatchDetailLoader(RiotApi riotApi, Executor executor, Region region, CommonDao<SummonerDto> summonerDao, CommonDao<MatchDetail> matchDetailDao, CommonDao<MatchReference> matchReferenceDao) {
        super(riotApi, executor);
        this.region = Objects.requireNonNull(region);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
        this.matchReferenceDao = Objects.requireNonNull(matchReferenceDao);
    }

    @Override
    public void execute() {
        while (true) {
            Optional<Entity<MatchReference>> matchReferenceEntity = matchReferenceDao.getRandom(region);
            if (matchReferenceEntity.isPresent()) {
                long matchId = matchReferenceEntity.get().getItem().getMatchId();
                if (notLoaded(matchId)) {
                    MatchDetail matchDetail = load(matchId);
                    matchDetailDao.save(region, matchId, matchDetail);
                    if (matchDetail.getParticipantIdentities() != null) {
                        matchDetail.getParticipantIdentities().stream().map(ParticipantIdentity::getPlayer)
                                .map(SummonerDtoMapper::map)
                                .forEach(e -> summonerDao.save(region, e.getId(), e));
                    }
                }
            } else {
                LOGGER.warn("{} : No matchReference found", RegionPrinter.getRegion(region));
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }

    /**
     * Is the match not already in data base.
     *
     * @param matchId the match id
     * @return true if the match is not in the database
     */
    private boolean notLoaded(long matchId) {
        return matchDetailDao.get(region, matchId) == null;
    }

    /**
     * Load the ranked stats for a given summoner.
     *
     * @param matchId the match id
     * @return the RankedStatsDto
     */
    private MatchDetail load(long matchId) {
        loaderInformation.addRequest();
        loaderInformation.addRankedStatsRequest();
        Query<MatchDetail> query = riotApi.getMatchDetailById(matchId)
                .includeTimeline(true)
                .region(region);
        MatchDetail matchDetail = executor.execute(query, region, 5);

        if (matchDetail == null) {
            matchDetail = new MatchDetail();
            matchDetail.setMatchId(matchId);
        }
        LOGGER.info("{} : {} - {} ", RegionPrinter.getRegion(region), matchId, loaderInformation.printInformation());
        return matchDetail;
    }
}
