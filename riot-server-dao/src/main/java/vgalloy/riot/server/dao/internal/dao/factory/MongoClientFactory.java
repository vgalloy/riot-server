package vgalloy.riot.server.dao.internal.dao.factory;

import com.mongodb.MongoClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public final class MongoClientFactory {

    private static final Map<String, MongoClient> MONGO_CLIENT_MAP = new HashMap<>();

    /**
     * Constructor.
     * To prevent instantiation
     */
    private MongoClientFactory() {
        throw new AssertionError();
    }

    /**
     * Get the mongo client.
     *
     * @param databaseUrl the database url
     * @return the mongo client
     */
    public static MongoClient get(String databaseUrl) {
        Objects.requireNonNull(databaseUrl, "database url can not be null");
        MongoClient mongoClient = MONGO_CLIENT_MAP.get(databaseUrl);
        mongoClient = Optional.ofNullable(mongoClient).orElse(new MongoClient(databaseUrl));
        MONGO_CLIENT_MAP.put(databaseUrl, mongoClient);
        return mongoClient;
    }
}
