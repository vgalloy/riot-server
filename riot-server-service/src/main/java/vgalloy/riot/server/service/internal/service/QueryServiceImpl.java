package vgalloy.riot.server.service.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.service.api.model.Position;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.internal.service.mapper.PositionMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
}
