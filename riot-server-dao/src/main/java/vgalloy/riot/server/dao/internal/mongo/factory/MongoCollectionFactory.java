package vgalloy.riot.server.dao.internal.mongo.factory;

import java.util.Objects;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author Vincent Galloy - 19/09/16
 *         Created by Vincent Galloy on 19/09/16.
 */
public final class MongoCollectionFactory {

    private final MongoCollection<Document> mongoCollection;

    /**
     * Constructor.
     *
     * @param mongoDatabase  the mongo database
     * @param collectionName the collection name
     */
    public MongoCollectionFactory(MongoDatabase mongoDatabase, String collectionName) {
        Objects.requireNonNull(mongoDatabase);
        Objects.requireNonNull(collectionName);

        mongoCollection = mongoDatabase.getCollection(collectionName);
    }

    /**
     * Get a MongoCollection object.
     *
     * @return the MongoCollection
     */
    public MongoCollection<Document> get() {
        return mongoCollection;
    }
}
