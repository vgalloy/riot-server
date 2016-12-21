package vgalloy.riot.server.dao.internal.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * @author Vincent Galloy - 19/09/16
 *         Created by Vincent Galloy on 19/09/16.
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
    public MongoDatabaseFactory(MongoClient mongoClient, String databaseName) {
        Objects.requireNonNull(mongoClient);
        Objects.requireNonNull(databaseName);

        mongoDatabase = mongoClient.getDatabase(databaseName);
    }

    /**
     * Get a DB object.
     *
     * @return the DB object
     */
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

        MongoCollectionFactory mongoCollectionFactory = mongoCollectionFactoryMap.get(collectionName);
        mongoCollectionFactory = Optional.ofNullable(mongoCollectionFactory).orElseGet(() -> new MongoCollectionFactory(mongoDatabase, collectionName));
        mongoCollectionFactoryMap.put(collectionName, mongoCollectionFactory);

        return mongoCollectionFactory;
    }
}
