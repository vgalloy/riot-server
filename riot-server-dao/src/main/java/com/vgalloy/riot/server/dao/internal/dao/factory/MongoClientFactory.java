package com.vgalloy.riot.server.dao.internal.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.mongodb.MongoClient;

/**
 * Created by Vincent Galloy on 14/06/16.
 *
 * @author Vincent Galloy
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

        return mongoDatabaseFactoryMap.computeIfAbsent(databaseName, e -> new MongoDatabaseFactory(mongoClient, databaseName));
    }

    /**
     * Get a DBFactory object.
     *
     * @param dbName the DB name
     * @return the DBFactory
     */
    public DBFactory getDB(String dbName) {
        Objects.requireNonNull(dbName);

        return mongoDBFactoryMap.computeIfAbsent(dbName, e -> new DBFactory(mongoClient, e));
    }
}
