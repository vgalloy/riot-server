package com.vgalloy.riot.server.service.internal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import com.vgalloy.riot.server.dao.api.dao.SummonerDao;
import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.GetSummonersQuery;
import com.vgalloy.riot.server.dao.api.entity.dpoid.CommonDpoId;
import com.vgalloy.riot.server.loader.api.service.LoaderClient;
import com.vgalloy.riot.server.service.api.model.summoner.GameSummary;
import com.vgalloy.riot.server.service.api.model.summoner.Summoner;
import com.vgalloy.riot.server.service.api.model.summoner.SummonerId;
import com.vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import com.vgalloy.riot.server.service.api.service.SummonerService;
import com.vgalloy.riot.server.service.internal.service.mapper.GameSummaryMapper;
import com.vgalloy.riot.server.service.internal.service.mapper.SummonerMapper;

/**
 * Created by Vincent Galloy on 23/08/16.
 *
 * @author Vincent Galloy
 */
public final class SummonerServiceImpl implements SummonerService {

    private final SummonerDao summonerDao;
    private final MatchDetailDao matchDetailDao;
    private final LoaderClient loaderClient;

    /**
     * Constructor.
     *
     * @param summonerDao    the summoner dao
     * @param matchDetailDao the match detail dao
     * @param loaderClient   the summoner loader client
     */
    public SummonerServiceImpl(SummonerDao summonerDao, MatchDetailDao matchDetailDao, LoaderClient loaderClient) {
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
        this.loaderClient = Objects.requireNonNull(loaderClient);
    }

    @Override
    public List<GameSummary> getLastGames(SummonerId summonerId, LocalDateTime from, LocalDateTime to) {
        return matchDetailDao.findMatchDetailBySummonerId(new CommonDpoId(summonerId.getRegion(), summonerId.getId()), from, to).stream()
            .map(e -> GameSummaryMapper.map(e, summonerId.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    @Override
    public ResourceWrapper<Summoner> get(SummonerId summonerId) {
        loaderClient.loadAsyncSummonerById(summonerId.getRegion(), summonerId.getId());
        return summonerDao.get(new CommonDpoId(summonerId.getRegion(), summonerId.getId()))
            .map(Entity::getItem)
            .map(e -> e.map(i -> SummonerMapper.map(summonerId.getRegion(), i))
                .map(ResourceWrapper::of)
                .orElseGet(ResourceWrapper::doesNotExist))
            .orElseGet(ResourceWrapper::notLoaded);
    }

    @Override
    public List<Summoner> getSummoners(GetSummonersQuery getSummonersQuery) {
        getSummonersQuery.getSummonersName()
            .forEach(summonerName -> getSummonersQuery.getRegions()
                .forEach(region -> loaderClient.loadAsyncSummonerByName(region, summonerName)));

        return summonerDao.getSummoners(getSummonersQuery)
            .stream()
            .map(SummonerMapper::map)
            .collect(Collectors.toList());
    }
}
