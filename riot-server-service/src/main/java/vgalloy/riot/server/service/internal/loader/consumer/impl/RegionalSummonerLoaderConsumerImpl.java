package vgalloy.riot.server.service.internal.loader.consumer.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.constant.SimpleQueueType;
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
import vgalloy.riot.server.dao.api.entity.itemid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.consumer.RegionalSummonerLoaderConsumer;
import vgalloy.riot.server.service.internal.loader.consumer.message.SummonerLoadingMessage;
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
    public void accept(SummonerLoadingMessage summonerLoadingMessage) {
        Objects.requireNonNull(summonerLoadingMessage);
        LOGGER.info("{} load full summoner : {}", RegionPrinter.getRegion(region), summonerLoadingMessage);
        if (SummonerLoadingMessage.LoaderType.BY_ID == summonerLoadingMessage.getLoaderType()) {
            loaderSummoner(summonerLoadingMessage.getSummonerId());
        } else {
            loaderSummoner(summonerLoadingMessage.getSummonerName());
        }
    }

    /**
     * Load the summoner, his ranked stats and all his recent games and save it.
     *
     * @param summonerId the summoner id
     */
    private void loaderSummoner(Long summonerId) {
        Objects.requireNonNull(summonerId);
        ItemId itemId = new ItemId(region, summonerId);

        /* Load and save the summoner */
        loadAndSaveSummoner(itemId);

        /* Load and save ranked stat */
        loadAndSaveRankedStat(itemId);

        /* Load and save recent game */
        loadAndSaveMatch(itemId);
    }

    /**
     * Load and save summoner by name.
     *
     * @param summonerName the summoner name
     */
    private void loaderSummoner(String summonerName) {
        Objects.requireNonNull(summonerName);
        Optional<SummonerDto> result = summonerDao.getSummonerByName(region, summonerName);
        if (result.isPresent()) {
            loaderSummoner(result.get().getId());
        } else {
            Map<String, SummonerDto> summonerDtoMap = executor.execute(riotApi.getSummonerByNames(summonerName), region, 1);
            if (summonerDtoMap == null || summonerDtoMap.size() == 0) {
                return;
            }
            long summonerId = summonerDtoMap.entrySet().iterator().next().getValue().getId();
            loaderSummoner(summonerId);
        }
    }

    /**
     * Load and save the summoner details.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveSummoner(ItemId summonerId) {
        if (shouldIdLoadThisSummoner(summonerId)) {
            LOGGER.info("{} load summoner : {}", RegionPrinter.getRegion(region), summonerId.getId());
            Map<String, SummonerDto> summonerDtoMap = executor.execute(riotApi.getSummonersByIds(summonerId.getId()), summonerId.getRegion(), 1);
            SummonerDto summonerDto = summonerDtoMap.entrySet().iterator().next().getValue();
            summonerDao.save(new CommonWrapper<>(summonerId, summonerDto));
        }
    }

    /**
     * Check if the summoner is ok for loading.
     *
     * @param summonerId the summoner id
     * @return true if the summoner can be loaded
     */
    private boolean shouldIdLoadThisSummoner(ItemId summonerId) {
        Optional<Entity<CommonWrapper<SummonerDto>>> optionalEntity = summonerDao.get(summonerId);
        if (!optionalEntity.isPresent()) {
            return true;
        }
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(optionalEntity.get().getLastUpdate(), 0, ZoneOffset.UTC);
        if (localDateTime.isBefore(LocalDateTime.now().minus(2, ChronoUnit.DAYS))) {
            return true;
        }
        return false;
    }

    /**
     * Load and save the ranked stats.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveRankedStat(ItemId summonerId) {
        if (shouldILoadThisRankedStat(summonerId)) {
            Query<RankedStatsDto> query = riotApi.getRankedStats(summonerId.getId());
            LOGGER.info("{} load rankedStat : {}", RegionPrinter.getRegion(region), summonerId.getId());
            Optional<RankedStatsDto> rankedStatsDto = Optional.ofNullable(executor.execute(query, summonerId.getRegion(), 1));
            if (rankedStatsDto.isPresent()) {
                rankedStatsDao.save(new CommonWrapper<>(summonerId, rankedStatsDto.get()));
            } else {
                rankedStatsDao.save(new CommonWrapper<>(summonerId));
            }
        }
    }

    /**
     * Check if the ranked stat is ok for loading.
     *
     * @param summonerId the summoner id
     * @return true if the ranked stat can be loaded
     */
    private boolean shouldILoadThisRankedStat(ItemId summonerId) {
        Optional<Entity<CommonWrapper<RankedStatsDto>>> optionalEntity = rankedStatsDao.get(summonerId);
        if (!optionalEntity.isPresent()) {
            return true;
        }
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(optionalEntity.get().getLastUpdate(), 0, ZoneOffset.UTC);
        if (localDateTime.isBefore(LocalDateTime.now().minus(2, ChronoUnit.HOURS))) {
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
                    .filter(this::shouldILoadThisMatch)
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
     * Check if the mach should be loaded.
     *
     * @param matchReference the match reference
     * @return true if the match should be loaded
     */
    private boolean shouldILoadThisMatch(MatchReference matchReference) {
        MatchDetailId matchDetailId = MatchDetailIdMapper.map(matchReference);
        if (matchDetailDao.get(matchDetailId).isPresent()) {
            return false;
        }
        if (matchDetailId.getMatchDate().isBefore(LocalDate.now().minus(1, ChronoUnit.MONTHS))) {
            return false;
        }
        if (SimpleQueueType.ARAM_5x5.equals(matchReference.getQueue())) {
            return false;
        }
        return true;
    }
}
