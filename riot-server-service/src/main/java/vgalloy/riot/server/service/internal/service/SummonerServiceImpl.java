package vgalloy.riot.server.service.internal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.internal.dao.impl.summoner.GetSummonersQuery;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.service.api.model.game.GameSummary;
import vgalloy.riot.server.service.api.model.summoner.Summoner;
import vgalloy.riot.server.service.api.model.summoner.SummonerId;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.service.internal.service.mapper.GameSummaryMapper;
import vgalloy.riot.server.service.internal.service.mapper.SummonerMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
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
        return matchDetailDao.findMatchDetailBySummonerId(summonerId.getRegion(), summonerId.getId(), from, to).stream()
                .map(e -> GameSummaryMapper.map(e, summonerId.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceWrapper<Summoner> get(SummonerId summonerId) {
        ResourceWrapper<Summoner> result = summonerDao.get(new DpoId(summonerId.getRegion(), summonerId.getId()))
                .map(Entity::getItem)
                .map(e -> e.map(i -> SummonerMapper.map(summonerId.getRegion(), i))
                        .map(ResourceWrapper::of)
                        .orElseGet(ResourceWrapper::doesNotExist))
                .orElseGet(ResourceWrapper::notLoaded);
        loaderClient.loadAsyncSummonerById(summonerId.getRegion(), summonerId.getId());
        return result;
    }

    @Override
    public List<Summoner> getSummoners(GetSummonersQuery getSummonersQuery) {
        List<Entity<SummonerDto, DpoId>> dbResult = summonerDao.getSummoners(getSummonersQuery);
        List<Summoner> result = new ArrayList<>();

        for (Entity<SummonerDto, DpoId> entity : dbResult) {
            if (entity.getItem().isPresent()) {
                result.add(SummonerMapper.map(entity.getItemId().getRegion(), entity.getItem().get()));
            } else {
                result.add(new Summoner(new SummonerId(entity.getItemId().getRegion(), entity.getItemId().getId()), null, null, null));
            }
        }

        if (result.size() == 1) {
            loaderClient.loadAsyncSummonerById(result.get(0).getSummonerId().getRegion(), result.get(0).getSummonerId().getId());
        }

        return result;
    }
}
