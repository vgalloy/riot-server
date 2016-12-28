package vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.loader.api.service.LoaderClient;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import vgalloy.riot.server.service.api.service.ChampionService;
import vgalloy.riot.server.service.api.service.exception.UserException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 08/12/16.
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
}
