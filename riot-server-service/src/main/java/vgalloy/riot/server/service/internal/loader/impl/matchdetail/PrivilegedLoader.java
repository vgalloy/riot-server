package vgalloy.riot.server.service.internal.loader.impl.matchdetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.matchlist.MatchList;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
public class PrivilegedLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegedLoader.class);

    private final CommonDao<MatchDetail> matchDetailDao;

    /**
     * Constructor.
     *
     * @param riotApi        the riot api
     * @param executor       the executor
     * @param matchDetailDao the match detail dao
     */
    public PrivilegedLoader(RiotApi riotApi, Executor executor, CommonDao<MatchDetail> matchDetailDao) {
        super(riotApi, executor);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
    }

    @Override
    public void execute() {
        Collection<Integer> summonerIdList = new ArrayList<>();
        summonerIdList.add(24550736); // Ivaranne
        summonerIdList.add(24540988); // Glenduil
        summonerIdList.add(24523231); // 3CS Ardemo
        summonerIdList.add(24541689); // Miir

        while (true) {
            for (Integer currentSummonerId : summonerIdList) {
                loaderInformation.addRequest();

                Query<MatchList> query = riotApi.getMatchListBySummonerId(currentSummonerId)
                        .region(Region.EUW);
                LOGGER.info("{} : matchList - {}", RegionPrinter.getRegion(Region.EUW), currentSummonerId);
                MatchList matchList = executor.execute(query, Region.EUW, 1);
                if (matchList != null) {
                    List<MatchReference> matchDetailList = matchList.getMatches();
                    if (matchDetailList != null) {
                        matchDetailList.stream()
                                .map(MatchReference::getMatchId)
                                .filter(this::notLoaded)
                                .map(this::load)
                                .forEach(e -> matchDetailDao.save(Region.EUW, e.getMatchId(), e));
                    }
                }
            }
            try {
                Thread.sleep(60 * 60 * 1000); // sleep one hour
            } catch (InterruptedException e) {
                throw new ServiceException(e);
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
        return !matchDetailDao.get(Region.EUW, matchId).isPresent();
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
                .region(Region.EUW);
        MatchDetail matchDetail = executor.execute(query, Region.EUW, 10);

        if (matchDetail == null) {
            matchDetail = new MatchDetail();
            matchDetail.setMatchId(matchId);
        }
        LOGGER.info("{} : matchDetail - {}", RegionPrinter.getRegion(Region.EUW), matchId);
        return matchDetail;
    }
}
