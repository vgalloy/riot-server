package vgalloy.riot.server.service.internal.loader.impl.matchdetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.matchlist.MatchList;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.itemid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.service.api.service.exception.ServiceException;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.AbstractLoader;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;
import vgalloy.riot.server.service.internal.service.mapper.MatchDetailIdMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 13/06/16.
 */
public class PrivilegedLoader extends AbstractLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegedLoader.class);

    private final MatchDetailDao matchDetailDao;

    /**
     * Constructor.
     *
     * @param riotApi        the riot api
     * @param executor       the executor
     * @param matchDetailDao the match detail dao
     */
    public PrivilegedLoader(RiotApi riotApi, Executor executor, MatchDetailDao matchDetailDao) {
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
        summonerIdList.add(24680794); // LeFielon
        summonerIdList.add(23056233); // Forlio

        while (true) {
            for (Integer currentSummonerId : summonerIdList) {
                loaderInformation.addRequest();

                Query<MatchList> query = riotApi.getMatchListBySummonerId(currentSummonerId)
                        .region(Region.EUW);
                MatchList matchList = executor.execute(query, Region.EUW, 1);
                LOGGER.info("{} : matchList {}", RegionPrinter.getRegion(Region.EUW), currentSummonerId);
                if (matchList != null) {
                    List<MatchReference> matchDetailList = matchList.getMatches();
                    if (matchDetailList != null) {
                        matchDetailList.stream()
                                .map(MatchDetailIdMapper::map)
                                .filter(this::notLoaded)
                                .map(this::load)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .map(e -> new MatchDetailWrapper(MatchDetailIdMapper.map(e), e))
                                .forEach(matchDetailDao::save);
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
    private boolean notLoaded(MatchDetailId matchId) {
        return !matchDetailDao.get(matchId).isPresent();
    }

    /**
     * Load the ranked stats for a given summoner.
     *
     * @param matchId the match id
     * @return the RankedStatsDto
     */
    private Optional<MatchDetail> load(MatchDetailId matchId) {
        loaderInformation.addRequest();
        Query<MatchDetail> query = riotApi.getMatchDetailById(matchId.getId())
                .includeTimeline(true)
                .region(Region.EUW);
        MatchDetail matchDetail = executor.execute(query, Region.EUW, 10);
        LOGGER.info("{} : matchDetail {}", RegionPrinter.getRegion(Region.EUW), matchId);
        return Optional.ofNullable(matchDetail);
    }
}
