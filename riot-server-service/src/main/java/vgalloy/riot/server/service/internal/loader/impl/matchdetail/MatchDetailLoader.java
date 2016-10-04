package vgalloy.riot.server.service.internal.loader.impl.matchdetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.ItemWrapper;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.LoaderHelper;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;
import vgalloy.riot.server.service.internal.loader.mapper.SummonerDtoMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 *         Need : MatchReference
 *         Load : MatchDetail && Summoner
 */
public class MatchDetailLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDetailLoader.class);

    private final Region region;
    private final SummonerDao summonerDao;
    private final MatchDetailDao matchDetailDao;
    private final MatchReferenceDao matchReferenceDao;

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
    public MatchDetailLoader(RiotApi riotApi, Executor executor, Region region, SummonerDao summonerDao, MatchDetailDao matchDetailDao, MatchReferenceDao matchReferenceDao) {
        super(riotApi, executor);
        this.region = Objects.requireNonNull(region);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
        this.matchReferenceDao = Objects.requireNonNull(matchReferenceDao);
    }

    @Override
    public void execute() {
        while (true) {
            long matchId = LoaderHelper.getRandomMatchId(matchReferenceDao, region, LOGGER);
            if (notLoaded(matchId)) {
                Optional<MatchDetail> matchDetail = load(matchId);
                if (matchDetail.isPresent()) {
                    matchDetailDao.save(matchDetail.get());
                    if (matchDetail.get().getParticipantIdentities() != null) {
                        matchDetail.get().getParticipantIdentities().stream()
                                .map(ParticipantIdentity::getPlayer)
                                .map(SummonerDtoMapper::map)
                                .forEach(e -> summonerDao.save(new ItemWrapper<>(region, e.getId(), e)));
                    }
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
        return !matchDetailDao.get(region, matchId, LocalDate.now().minus(10, ChronoUnit.DAYS)).isPresent();
    }

    /**
     * Load the match detail for a given match id.
     *
     * @param matchId the match id
     * @return the RankedStatsDto
     */
    private Optional<MatchDetail> load(long matchId) {
        loaderInformation.addRequest();
        Query<MatchDetail> query = riotApi.getMatchDetailById(matchId)
                .includeTimeline(true)
                .region(region);
        LOGGER.info("{} : matchDetail {}", RegionPrinter.getRegion(region), matchId);

        return Optional.ofNullable(executor.execute(query, region, 5));
    }
}
