package vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.exception.UserException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 *         // TODO Remove ?
 */
public final class QueryServiceImpl implements QueryService {

    private final ChampionDao championDao;

    /**
     * Constructor.
     *
     * @param championDao the champion dao
     */
    public QueryServiceImpl(ChampionDao championDao) {
        this.championDao = Objects.requireNonNull(championDao);
    }

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        return championDao.getWinRate(championId);
    }

    @Override
    public Map<LocalDate, WinRate> getWinRate(int championId, LocalDate startDate, LocalDate endDate) {
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

        return championDao.getWinRate(championId, startDate, endDate);
    }
}
