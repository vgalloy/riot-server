package com.vgalloy.riot.server.dao.internal.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Created by Vincent Galloy on 19/09/16.
 *
 * @author Vincent Galloy
 */
public final class MongoDatabaseFactory implements Supplier<MongoDatabase> {

    private final Map<String, MongoCollectionFactory> mongoCollectionFactoryMap = new HashMap<>();
    private final MongoDatabase mongoDatabase;

    /**
     * Constructor.
     *
     * @param mongoClient  the mongo client
     * @param databaseName the database name
     */
    MongoDatabaseFactory(MongoClient mongoClient, String databaseName) {
        Objects.requireNonNull(mongoClient);
        Objects.requireNonNull(databaseName);

        mongoDatabase = mongoClient.getDatabase(databaseName);
    }

    @Override
    public MongoDatabase get() {
        return mongoDatabase;
    }

    /**
     * Get a MongoCollectionFactory object.
     *
     * @param collectionName the collection name
     * @return the MongoCollectionFactory
     */
    public MongoCollectionFactory getMongoCollection(String collectionName) {
        Objects.requireNonNull(collectionName);

        return mongoCollectionFactoryMap.computeIfAbsent(collectionName, e -> new MongoCollectionFactory(mongoDatabase, e));
    }
}
