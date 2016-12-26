package vgalloy.riot.server.loader.internal.loader.impl;

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
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.ParticipantIdentity;
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
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.dao.api.mapper.MatchDetailIdMapper;
import vgalloy.riot.server.loader.internal.executor.Executor;
import vgalloy.riot.server.loader.internal.helper.RegionPrinter;
import vgalloy.riot.server.loader.internal.loader.SummonerLoader;
import vgalloy.riot.server.loader.internal.loader.mapper.SummonerMapper;

/**
 * @author Vincent Galloy - 10/10/16
 *         Created by Vincent Galloy on 10/10/16.
 */
public final class SummonerLoaderImpl implements SummonerLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SummonerLoaderImpl.class);

    private final RiotApi riotApi;
    private final Executor executor;
    private final SummonerDao summonerDao;
    private final MatchDetailDao matchDetailDao;
    private final RankedStatsDao rankedStatsDao;

    /**
     * Constructor.
     *
     * @param riotApi        the riot api
     * @param executor       the executor
     * @param summonerDao    the summoner dao
     * @param matchDetailDao the match detail dao
     * @param rankedStatsDao the ranked stats dao
     */
    public SummonerLoaderImpl(RiotApi riotApi, Executor executor, SummonerDao summonerDao, MatchDetailDao matchDetailDao, RankedStatsDao rankedStatsDao) {
        this.riotApi = Objects.requireNonNull(riotApi);
        this.executor = Objects.requireNonNull(executor);
        this.summonerDao = Objects.requireNonNull(summonerDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
        this.rankedStatsDao = Objects.requireNonNull(rankedStatsDao);
    }

    @Override
    public void loadSummonerById(Region region, Long summonerId) {
        Objects.requireNonNull(summonerId);
        LOGGER.info("{} load full summoner with id : {}", RegionPrinter.getRegion(region), summonerId);
        DpoId dpoId = new DpoId(region, summonerId);

        if (shouldIdLoadThisSummoner(dpoId)) {
            /* Load and save the summoner */
            loadAndSaveSummoner(dpoId);
            /* Load and save ranked stat */
            loadAndSaveRankedStat(dpoId);
            /* Load and save recent game */
            loadAndSaveMatch(dpoId);
        }
    }

    @Override
    public void loadSummonerByName(Region region, String summonerName) {
        Objects.requireNonNull(summonerName);
        LOGGER.info("{} load full summoner with name : {}", RegionPrinter.getRegion(region), summonerName);
        Optional<Long> summonerId = findSummonerId(region, summonerName);
        if (summonerId.isPresent()) {
            loadSummonerById(region, summonerId.get());
        } else {
            LOGGER.warn("{} Can not find summoner id for the summoner name : {}", RegionPrinter.getRegion(region), summonerName);
        }
    }

    /**
     * Find the summoner id for the summoner name. First try with database then riot api.
     *
     * @param region       the region of the summoner
     * @param summonerName the summoner name
     * @return the summoner id
     */
    private Optional<Long> findSummonerId(Region region, String summonerName) {
        Optional<SummonerDto> result = summonerDao.getSummonerByName(region, summonerName);
        if (result.isPresent()) {
            return Optional.of(result.get().getId());
        } else {
            Map<String, SummonerDto> summonerDtoMap = executor.execute(riotApi.getSummonerByNames(summonerName), region, 1);
            if (summonerDtoMap == null || summonerDtoMap.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(summonerDtoMap.entrySet().iterator().next().getValue().getId());
        }
    }

    /**
     * Load and save the summoner details.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveSummoner(DpoId summonerId) {
        LOGGER.info("{} load summoner : {}", RegionPrinter.getRegion(summonerId.getRegion()), summonerId.getId());
        Map<String, SummonerDto> summonerDtoMap = executor.execute(riotApi.getSummonersByIds(summonerId.getId()), summonerId.getRegion(), 1);
        SummonerDto summonerDto = summonerDtoMap.entrySet().iterator().next().getValue();
        summonerDao.save(new CommonDpoWrapper<>(summonerId, summonerDto));
    }

    /**
     * Check if the summoner is ok for loading.
     *
     * @param summonerId the summoner id
     * @return true if the summoner can be loaded
     */
    private boolean shouldIdLoadThisSummoner(DpoId summonerId) {
        Optional<Entity<SummonerDto, DpoId>> optionalEntity = summonerDao.get(summonerId);
        if (!optionalEntity.isPresent()) {
            return true;
        }
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(optionalEntity.get().getLastUpdate(), 0, ZoneOffset.UTC);
        if (localDateTime.isBefore(LocalDateTime.now().minus(20, ChronoUnit.MINUTES))) {
            return true;
        }
        return false;
    }

    /**
     * Load and save the ranked stats.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveRankedStat(DpoId summonerId) {
        if (shouldILoadThisRankedStat(summonerId)) {
            Query<RankedStatsDto> query = riotApi.getRankedStats(summonerId.getId());
            LOGGER.info("{} load rankedStat : {}", RegionPrinter.getRegion(summonerId.getRegion()), summonerId.getId());
            Optional<RankedStatsDto> rankedStatsDto = Optional.ofNullable(executor.execute(query, summonerId.getRegion(), 1));
            if (rankedStatsDto.isPresent()) {
                rankedStatsDao.save(new CommonDpoWrapper<>(summonerId, rankedStatsDto.get()));
            } else {
                rankedStatsDao.save(new CommonDpoWrapper<>(summonerId));
            }
        }
    }

    /**
     * Check if the ranked stat is ok for loading.
     *
     * @param summonerId the summoner id
     * @return true if the ranked stat can be loaded
     */
    private boolean shouldILoadThisRankedStat(DpoId summonerId) {
        Optional<Entity<RankedStatsDto, DpoId>> optionalEntity = rankedStatsDao.get(summonerId);
        if (!optionalEntity.isPresent()) {
            return true;
        }
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(optionalEntity.get().getLastUpdate(), 0, ZoneOffset.UTC);
        return localDateTime.isBefore(LocalDateTime.now().minus(2, ChronoUnit.HOURS));
    }

    /**
     * Load and save the recent match details.
     *
     * @param summonerId the summoner essential information
     */
    private void loadAndSaveMatch(DpoId summonerId) {
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
            LOGGER.info("{} load matchDetail : {}", RegionPrinter.getRegion(summonerId.getRegion()), matchId);
            MatchDetail result = executor.execute(riotApi.getMatchDetailById(matchId).includeTimeline(true), summonerId.getRegion(), 1);
            if (result != null) {
                matchDetailDao.save(new MatchDetailWrapper(MatchDetailIdMapper.map(result), result));
                extractPlayer(result);
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
        return true;
    }

    /**
     * .
     * Extract player for match detail.
     *
     * @param matchDetail the match detail
     */
    private void extractPlayer(MatchDetail matchDetail) {
        if (matchDetail.getParticipantIdentities() == null) {
            return;
        }
        matchDetail.getParticipantIdentities().stream()
                .filter(e -> e != null)
                .map(ParticipantIdentity::getPlayer)
                .filter(e -> e != null)
                .map(SummonerMapper::map)
                .map(e -> new CommonDpoWrapper<>(new DpoId(matchDetail.getRegion(), e.getId()), e))
                .filter(e -> !summonerDao.get(e.getItemId()).isPresent())
                .forEach(summonerDao::save);
    }
}
