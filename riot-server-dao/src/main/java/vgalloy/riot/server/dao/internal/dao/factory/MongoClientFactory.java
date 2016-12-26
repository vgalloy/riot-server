package vgalloy.riot.server.dao.internal.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import com.mongodb.MongoClient;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public final class MongoClientFactory implements Supplier<MongoClient> {

    private final Map<String, MongoDatabaseFactory> mongoDatabaseFactoryMap = new HashMap<>();
    private final Map<String, DBFactory> mongoDBFactoryMap = new HashMap<>();
    private final MongoClient mongoClient;

    /**
     * Constructor.
     *
     * @param databaseUrl the database url
     */
    MongoClientFactory(String databaseUrl) {
        Objects.requireNonNull(databaseUrl);

        mongoClient = new MongoClient(databaseUrl);
    }

    @Override
    public MongoClient get() {
        return mongoClient;
    }

    /**
     * Get a MongoDatabaseFactory object.
     *
     * @param databaseName the database name
     * @return the MongoDatabaseFactory
     */
    public MongoDatabaseFactory getMongoDatabase(String databaseName) {
        Objects.requireNonNull(databaseName);

        MongoDatabaseFactory mongoDatabaseFactory = mongoDatabaseFactoryMap.get(databaseName);
        mongoDatabaseFactory = Optional.ofNullable(mongoDatabaseFactory).orElseGet(() -> new MongoDatabaseFactory(mongoClient, databaseName));
        mongoDatabaseFactoryMap.put(databaseName, mongoDatabaseFactory);

        return mongoDatabaseFactory;
    }

    /**
     * Get a DBFactory object.
     *
     * @param dbName the DB name
     * @return the DBFactory
     */
    public DBFactory getDB(String dbName) {
        Objects.requireNonNull(dbName);

        DBFactory db = mongoDBFactoryMap.get(dbName);
        db = Optional.ofNullable(db).orElseGet(() -> new DBFactory(mongoClient, dbName));
        mongoDBFactoryMap.put(dbName, db);

        return db;
    }
}
