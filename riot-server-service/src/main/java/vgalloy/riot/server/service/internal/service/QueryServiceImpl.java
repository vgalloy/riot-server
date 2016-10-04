package vgalloy.riot.server.service.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.service.api.service.QueryService;

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
        return matchDetailDao.getWinRate(championId, startDate, endDate);
    }
}
