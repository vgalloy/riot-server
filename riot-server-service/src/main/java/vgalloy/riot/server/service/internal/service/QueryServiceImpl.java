package vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.exception.UserException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class QueryServiceImpl implements QueryService {

    private final QueryDao queryDao;
    private final MatchDetailDao matchDetailDao;

    /**
     * Constructor.
     *
     * @param queryDao       the query dao
     * @param matchDetailDao the match detail dao
     */
    public QueryServiceImpl(QueryDao queryDao, MatchDetailDao matchDetailDao) {
        this.queryDao = Objects.requireNonNull(queryDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
    }

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        return queryDao.getWinRate(championId);
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

        return matchDetailDao.getWinRate(championId, startDate, endDate);
    }
}
