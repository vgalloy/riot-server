package vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.api.service.exception.UserException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Component
public final class QueryServiceImpl implements QueryService {

    @Autowired
    private QueryDao queryDao;
    @Autowired
    private MatchDetailDao matchDetailDao;

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
