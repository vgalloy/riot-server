package vgalloy.riot.server.dao.internal.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * @author Vincent Galloy - 19/09/16
 *         Created by Vincent Galloy on 19/09/16.
 */
public final class DBFactory {

    private final Map<String, DBCollectionFactory> dbCollectionFactoryMap = new HashMap<>();
    private final DB db;

    /**
     * Constructor.
     *
     * @param mongoClient the mongo client
     * @param dbName      the db name
     */
    public DBFactory(MongoClient mongoClient, String dbName) {
        Objects.requireNonNull(mongoClient);
        Objects.requireNonNull(dbName);

        db = mongoClient.getDB(dbName);
    }

    /**
     * Get a DB object.
     *
     * @return the DB
     */
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

        DBCollectionFactory dbCollectionFactory = dbCollectionFactoryMap.get(dbCollectionName);
        if (dbCollectionFactory == null) {
            dbCollectionFactory = new DBCollectionFactory(db, dbCollectionName);
        }
        dbCollectionFactoryMap.put(dbCollectionName, dbCollectionFactory);

        return dbCollectionFactory;
    }
}
