package vgalloy.riot.server.dao.internal.dao.query.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.entity.Position;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.internal.dao.factory.MongoClientFactory;
import vgalloy.riot.server.dao.internal.query.PositionQuery;
import vgalloy.riot.server.dao.internal.query.WinRateQuery;
import vgalloy.riot.server.dao.internal.task.factory.TaskFactory;
import vgalloy.riot.server.dao.internal.task.impl.UpdatePositionTask;
import vgalloy.riot.server.dao.internal.task.impl.UpdateWinRateTask;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public final class QueryDaoImpl implements QueryDao {

    private final MongoDatabase mongoDatabase;

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public QueryDaoImpl(String databaseUrl, String databaseName) {
        MongoClient mongoClient = MongoClientFactory.get(databaseUrl);
        mongoDatabase = mongoClient.getDatabase(databaseName);
        TaskFactory.startTask(new UpdatePositionTask(mongoDatabase), 15 * 60 * 1000);
        TaskFactory.startTask(new UpdateWinRateTask(mongoDatabase), 15 * 60 * 1000);
    }

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        return WinRateQuery.getWinRate(mongoDatabase, championId);
    }

    @Override
    public List<List<Position>> getPosition(long summonerId, int championId) {
        return PositionQuery.getPosition(mongoDatabase, summonerId, championId);
    }

    @Override
    public Map<LocalDate, WinRate> getWinRate(int championId, LocalDate startDate, LocalDate endDate) {
        return WinRateQuery.getWinRate(mongoDatabase, championId, startDate, endDate);
    }
}
