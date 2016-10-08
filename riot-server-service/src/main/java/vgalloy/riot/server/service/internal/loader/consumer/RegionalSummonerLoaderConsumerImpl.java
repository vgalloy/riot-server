package vgalloy.riot.server.service.internal.loader.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.matchlist.MatchList;
import vgalloy.riot.api.api.dto.matchlist.MatchReference;
import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.api.api.query.Query;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.consumer.impl.RegionalSummonerLoaderConsumer;
import vgalloy.riot.server.service.internal.loader.helper.RegionPrinter;
import vgalloy.riot.server.service.internal.service.mapper.MatchDetailIdMapper;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public class RegionalSummonerLoaderConsumerImpl implements RegionalSummonerLoaderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionalSummonerLoaderConsumerImpl.class);

    private final Region region;
    private final RiotApi riotApi;
    private final Executor executor;
    private final SummonerDao summonerDao;
    private final MatchDetailDao matchDetailDao;
    private final RankedStatsDao rankedStatsDao;

    /**
     * Constructor.
     *
     * @param region         the region
     * @param riotApi        the riot api
     * @param executor       the executor
     * @param summonerDao    the summoner dao
     * @param matchDetailDao the match detail dao
     * @param rankedStatsDao the ranked stats dao
     */
    public RegionalSummonerLoaderConsumerImpl(Region region, RiotApi riotApi, Executor executor, SummonerDao summonerDao, MatchDetailDao matchDetailDao, RankedStatsDao rankedStatsDao) {
        this.region = Objects.requireNonNull(region);
        this.riotApi = Objects.requireNonNull(riotApi);
        this.executor = Objects.requireNonNull(executor);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
        this.rankedStatsDao = Objects.requireNonNull(rankedStatsDao);
    }

    @Override
    public void loaderSummoner(Long summonerId) {
        Objects.requireNonNull(summonerId);
        LOGGER.info("{} load full summoner : {}", RegionPrinter.getRegion(region), summonerId);

        ItemId itemId = new ItemId(region, summonerId);
        if (isSummonerOkForLoading(itemId)) {
            /* Load and save the summoner */
            loadAndSaveSummoner(itemId);

            /* Load and save ranked stat */
            loadAndSaveRankedStat(itemId);

            /* Load and save recent game */
            loadAndSaveMatch(itemId);
        }
    }

    @Override
    public void accept(Long summonerId) {
        loaderSummoner(summonerId);
    }

    /**
     * Check the summoner is ok for loading.
     *
     * @param summonerId the summoner id
     * @return true if the summoner can be loaded
     */
    private boolean isSummonerOkForLoading(ItemId summonerId) {
        Optional<Entity<CommonWrapper<SummonerDto>>> optionalEntity = summonerDao.get(summonerId);
        if (!optionalEntity.isPresent()) {
            return true;
        }
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(optionalEntity.get().getLastUpdate(), 0, ZoneOffset.UTC);
        if (localDateTime.isBefore(LocalDateTime.now().minus(2, ChronoUnit.MINUTES))) {
            return true;
        }
        return false;
    }

    /**
     * Load and save the recent match details.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveMatch(ItemId summonerId) {
        Query<MatchList> query = riotApi.getMatchListBySummonerId(summonerId.getId()).region(summonerId.getRegion());
        MatchList matchList = executor.execute(query, summonerId.getRegion(), 1);
        List<Long> matchIdList = new ArrayList<>();
        if (matchList != null && matchList.getMatches() != null) {
            matchIdList = matchList.getMatches().stream()
                    .filter(e -> !matchDetailDao.get(MatchDetailIdMapper.map(e)).isPresent())
                    .map(MatchReference::getMatchId)
                    .collect(Collectors.toList());
        }

        for (Long matchId : matchIdList) {
            LOGGER.info("{} load matchDetail : {}", RegionPrinter.getRegion(region), matchId);
            MatchDetail result = executor.execute(riotApi.getMatchDetailById(matchId).includeTimeline(true), summonerId.getRegion(), 1);
            if (result != null) {
                matchDetailDao.save(new MatchDetailWrapper(MatchDetailIdMapper.map(result), result));
            }
        }
    }

    /**
     * Load and save the ranked stats.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveRankedStat(ItemId summonerId) {
        Query<RankedStatsDto> query = riotApi.getRankedStats(summonerId.getId());
        LOGGER.info("{} load rankedStat : {}", RegionPrinter.getRegion(region), summonerId.getId());
        Optional<RankedStatsDto> rankedStatsDto = Optional.ofNullable(executor.execute(query, summonerId.getRegion(), 1));
        if (rankedStatsDto.isPresent()) {
            rankedStatsDao.save(new CommonWrapper<>(summonerId, rankedStatsDto.get()));
        } else {
            rankedStatsDao.save(new CommonWrapper<>(summonerId));
        }
    }

    /**
     * Load and save the summoner details.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveSummoner(ItemId summonerId) {
        LOGGER.info("{} load summoner : {}", RegionPrinter.getRegion(region), summonerId.getId());
        Map<String, SummonerDto> summonerDtoMap = executor.execute(riotApi.getSummonersByIds(summonerId.getId()), summonerId.getRegion(), 1);
        SummonerDto summonerDto = summonerDtoMap.entrySet().iterator().next().getValue();
        summonerDao.save(new CommonWrapper<>(summonerId, summonerDto));
    }
}
