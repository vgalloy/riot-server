package vgalloy.riot.server.service.internal.executor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.executor.Runner;
import vgalloy.riot.server.service.internal.loader.Loader;
import vgalloy.riot.server.service.internal.loader.impl.intializer.LoaderInitializer;
import vgalloy.riot.server.service.internal.loader.impl.matchdetail.MatchDetailLoader;
import vgalloy.riot.server.service.internal.loader.impl.matchdetail.PrivilegedLoader;
import vgalloy.riot.server.service.internal.loader.impl.matchreference.MatchReferenceLoader;
import vgalloy.riot.server.service.internal.loader.impl.rankedstats.RankedStatsLoader;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
public class RunnerImpl implements Runner {

    private final List<Loader> loaderList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param riotApi           the riot api
     * @param executor          the executor
     * @param summonerDao       the summoner dao
     * @param matchDetailDao    the match detail dao
     * @param matchReferenceDao the match reference dao
     * @param rankedStatsDao    the ranked stats dao
     */
    public RunnerImpl(RiotApi riotApi,
                      Executor executor,
                      CommonDao<SummonerDto> summonerDao,
                      CommonDao<MatchDetail> matchDetailDao,
                      CommonDao<MatchReference> matchReferenceDao,
                      CommonDao<RankedStatsDto> rankedStatsDao) {

        register(new LoaderInitializer(riotApi, executor, summonerDao));

        Collection<Region> regionList = new ArrayList<>();
        regionList.add(Region.EUW);
        regionList.add(Region.EUNE);
        regionList.add(Region.KR);
        regionList.add(Region.NA);
        regionList.add(Region.BR);

        for (Region region : regionList) {
            register(new MatchDetailLoader(riotApi, executor, region, summonerDao, matchDetailDao, matchReferenceDao));
            register(new MatchReferenceLoader(riotApi, executor, region, summonerDao, matchReferenceDao));
            register(new RankedStatsLoader(riotApi, executor, region, summonerDao, rankedStatsDao));
        }

        register(new PrivilegedLoader(riotApi, executor, matchDetailDao));
    }

    /**
     * Register the loader into the runner and make it start.
     *
     * @param loader the loader
     */
    private void register(Loader loader) {
        loaderList.add(loader);
        new Thread(loader).start();
    }

    @Override
    public List<Loader> getLoaderList() {
        return Collections.unmodifiableList(loaderList);
    }
}
