package vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.service.api.model.LastGame;
import vgalloy.riot.server.service.api.service.SummonerService;
import vgalloy.riot.server.service.internal.loader.client.SummonerLoaderClient;
import vgalloy.riot.server.service.internal.service.mapper.LastGameMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class SummonerServiceImpl extends AbstractService<SummonerDto> implements SummonerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerServiceImpl.class);

    private final SummonerDao summonerDao;
    private final MatchDetailDao matchDetailDao;
    private final SummonerLoaderClient summonerLoaderClient;

    /**
     * Constructor.
     *
     * @param summonerDao          the summoner dao
     * @param matchDetailDao       the match detail dao
     * @param summonerLoaderClient the summoner loader client
     */
    public SummonerServiceImpl(SummonerDao summonerDao, MatchDetailDao matchDetailDao, SummonerLoaderClient summonerLoaderClient) {
        super(summonerDao);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
        this.summonerLoaderClient = Objects.requireNonNull(summonerLoaderClient);
    }

    @Override
    public List<LastGame> getLastGames(Region region, long summonerId, LocalDate from, LocalDate to) {
        LOGGER.info("getLastGames : {} {} {} {}", region, summonerId, from, to);
        return matchDetailDao.findMatchDetailBySummonerId(region, summonerId, from, to.plus(1, ChronoUnit.DAYS)).stream()
                .map(e -> LastGameMapper.map(e, summonerId))
                .sorted((o1, o2) -> (int) (o1.getMatchCreation() - o2.getMatchCreation()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SummonerDto> getSummonerByName(Region region, String summonerName) {
        summonerLoaderClient.loaderSummoner(region, summonerName);
        return summonerDao.getSummonerByName(region, summonerName);
    }
}
