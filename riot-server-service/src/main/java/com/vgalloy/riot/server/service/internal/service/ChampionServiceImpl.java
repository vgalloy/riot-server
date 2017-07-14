package com.vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;

import com.vgalloy.riot.server.dao.api.dao.ChampionDao;
import com.vgalloy.riot.server.dao.api.entity.ChampionName;
import com.vgalloy.riot.server.dao.api.entity.Entity;
import com.vgalloy.riot.server.dao.api.entity.WinRate;
import com.vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import com.vgalloy.riot.server.loader.api.service.LoaderClient;
import com.vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import com.vgalloy.riot.server.service.api.service.ChampionService;
import com.vgalloy.riot.server.service.api.service.exception.UserException;

/**
 * Created by Vincent Galloy on 08/12/16.
 *
 * @author Vincent Galloy
 */
public final class ChampionServiceImpl implements ChampionService {

    private final LoaderClient loaderClient;
    private final ChampionDao championDao;

    /**
     * Constructor.
     *
     * @param championDao  the champion dao
     * @param loaderClient the summoner loader client
     */
    public ChampionServiceImpl(ChampionDao championDao, LoaderClient loaderClient) {
        this.loaderClient = Objects.requireNonNull(loaderClient);
        this.championDao = Objects.requireNonNull(championDao);
    }

    @Override
    public ResourceWrapper<ChampionDto> get(DpoId dpoId) {
        loaderClient.loadChampionById(dpoId.getRegion(), dpoId.getId());
        return championDao.get(dpoId)
            .map(Entity::getItem)
            .map(e -> e.map(ResourceWrapper::of)
                .orElseGet(ResourceWrapper::doesNotExist))
            .orElseGet(ResourceWrapper::notLoaded);
    }

    @Override
    public Map<Integer, WinRate> getWinRateForAllChampion(LocalDate date) {
        return championDao.getWinRateForAllChampion(date);
    }

    @Override
    public Map<Integer, Double> getWinRateByGamePlayed(int championId) {
        return championDao.getWinRateByGamePlayed(championId);
    }

    @Override
    public Map<LocalDate, WinRate> getWinRateDuringPeriodOfTime(int championId, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);

        if (startDate.isAfter(endDate)) {
            throw new UserException("startDate must be after endDate");
        }
        if (startDate.isBefore(LocalDate.now().minus(4, ChronoUnit.YEARS))) {
            throw new UserException("startDate " + startDate + " is to old");
        }
        if (endDate.isAfter(LocalDate.now().plus(2, ChronoUnit.DAYS))) {
            throw new UserException("endDate " + endDate + " is in the future");
        }

        return championDao.getWinRateDuringPeriodOfTime(championId, startDate, endDate);
    }

    @Override
    public List<ChampionName> autoCompleteChampionName(Region region, String championName) {
        return championDao.autoCompleteChampionName(region, championName, 10);
    }
}
