package vgalloy.riot.server.service.internal.loader.impl.matchdetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vgalloy.riot.api.rest.constant.Region;
import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchList;
import vgalloy.riot.api.rest.request.matchlist.dto.MatchReference;
import vgalloy.riot.api.service.RiotApi;
import vgalloy.riot.api.service.query.Query;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
@Component
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
                        .region(Region.euw);
                LOGGER.info("get Match list {}", currentSummonerId);
                MatchList matchList = executor.execute(query, Region.euw, 1);
                if (matchList != null) {
                    List<MatchReference> matchDetailList = matchList.getMatches();
                    if (matchDetailList != null) {
                        matchDetailList.stream()
                                .map(MatchReference::getMatchId)
                                .filter(this::notLoaded)
                                .map(this::load)
                                .forEach(e -> matchDetailDao.save(Region.euw, e.getMatchId(), e));
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
        return matchDetailDao.get(Region.euw, matchId) == null;
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
                .region(Region.euw);
        MatchDetail matchDetail = executor.execute(query, Region.euw, 10);

        if (matchDetail == null) {
            matchDetail = new MatchDetail();
            matchDetail.setMatchId(matchId);
        }
        LOGGER.info("{} - {} ", matchId, loaderInformation.printInformation());
        return matchDetail;
    }
}
