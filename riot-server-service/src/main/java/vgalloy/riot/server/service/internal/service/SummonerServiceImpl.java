package vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.service.api.model.LastGame;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.service.internal.service.mapper.LastGameMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class SummonerServiceImpl extends AbstractService<SummonerDto> implements SummonerService {

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
        super(summonerDao);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
        this.loaderClient = Objects.requireNonNull(loaderClient);
    }

    @Override
    public List<LastGame> getLastGames(Region region, long summonerId, LocalDate from, LocalDate to) {
        return matchDetailDao.findMatchDetailBySummonerId(region, summonerId, from, to.plus(1, ChronoUnit.DAYS)).stream()
                .map(e -> LastGameMapper.map(e, summonerId))
                .sorted((o1, o2) -> Long.compare(o1.getMatchCreation(), o2.getMatchCreation()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SummonerDto> getSummonerByName(Region region, String summonerName) {
        loaderClient.loadAsyncSummonerByName(region, summonerName);
        return summonerDao.getSummonerByName(region, summonerName);
    }

    @Override
    public Optional<Model<SummonerDto>> get(DpoId dpoId) {
        loaderClient.loadAsyncSummonerById(dpoId.getRegion(), dpoId.getId());
        return super.get(dpoId);
    }
}
