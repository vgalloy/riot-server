package vgalloy.riot.server.dao.internal.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * Created by Vincent Galloy on 19/09/16.
 *
 * @author Vincent Galloy
 */
public final class DBFactory implements Supplier<DB> {

    private final Map<String, DBCollectionFactory> dbCollectionFactoryMap = new HashMap<>();
    private final DB db;

    /**
     * Constructor.
     *
     * @param mongoClient the mongo client
     * @param dbName      the db name
     */
    DBFactory(MongoClient mongoClient, String dbName) {
        Objects.requireNonNull(mongoClient);
        Objects.requireNonNull(dbName);

        db = mongoClient.getDB(dbName);
    }

    @Override
    public DB get() {
        return db;
    }

    /**
     * Get a DBCollectionFactory object.
     *
     * @param dbCollectionName the DB collection name
     * @return the DBCollectionFactory
     */
    public DBCollectionFactory getDBCollection(String dbCollectionName) {
        Objects.requireNonNull(dbCollectionName);

        return dbCollectionFactoryMap.computeIfAbsent(dbCollectionName, e -> new DBCollectionFactory(db, e));
    }
}
