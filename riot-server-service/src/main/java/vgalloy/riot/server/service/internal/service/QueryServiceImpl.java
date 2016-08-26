package vgalloy.riot.server.service.internal.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.service.api.model.Position;
import vgalloy.riot.server.service.api.service.QueryService;
import vgalloy.riot.server.service.internal.service.mapper.PositionMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Component
public class QueryServiceImpl implements QueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryServiceImpl.class);

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

    /**
     * Update the win rate table.
     */
    @Scheduled(fixedDelay = 15 * 60 * 1000) // 15 min
    public void updateWinRate() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ START ] : updateWinRate");
        queryDao.updateWinRate();
        LOGGER.info("[ END ] : updateWinRate {} ms", System.currentTimeMillis() - startTime);
    }

    /**
     * Update the position table.
     */
    @Scheduled(fixedDelay = 15 * 60 * 1000) // 15 min
    public void updatePosition() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ START ] : updatePosition");
        queryDao.updatePosition();
        LOGGER.info("[ END ] : updatePosition {} ms", System.currentTimeMillis() - startTime);
    }
}
