package vgalloy.riot.server.service.internal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.service.api.model.Position;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.internal.service.mapper.PositionMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Component
public class QueryServiceImpl implements QueryService {

    @Autowired
    private QueryDao queryDao;

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        return queryDao.getWinRate(championId);
    }

    @Override
    public List<List<Position>> getPosition(long summonerId, int championId) {
        return queryDao.getPosition(summonerId, championId).stream()
                .map(e -> e.stream().map(PositionMapper::map).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<LocalDate, WinRate> getWinRate(int championId, LocalDate startDate, LocalDate endDate) {
        return queryDao.getWinRate(championId, startDate, endDate);
    }
}
