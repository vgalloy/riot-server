package vgalloy.riot.server.dao.internal.dao.query.impl;

import java.util.Map;

import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDatabaseFactory;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import vgalloy.riot.server.dao.internal.query.WinRateQuery;
import vgalloy.riot.server.dao.internal.task.factory.TaskFactory;
import vgalloy.riot.server.dao.internal.task.impl.UpdateWinRateTask;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public final class QueryDaoImpl implements QueryDao {

    private final MongoDatabaseFactory mongoDatabaseFactory;

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public QueryDaoImpl(String databaseUrl, String databaseName) {
        mongoDatabaseFactory = MongoDriverObjectFactory.getMongoClient(databaseUrl).getMongoDatabase(databaseName);
        TaskFactory.startTask(new UpdateWinRateTask(mongoDatabaseFactory), 15 * 60 * 1000);
    }

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        return WinRateQuery.getWinRate(mongoDatabaseFactory, championId);
    }
}
