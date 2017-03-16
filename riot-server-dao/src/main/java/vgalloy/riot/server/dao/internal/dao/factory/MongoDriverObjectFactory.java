package vgalloy.riot.server.dao.internal.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Vincent Galloy on 05/10/16.
 *
 * @author Vincent Galloy
 */
public final class MongoDriverObjectFactory {

    private static final Map<String, MongoClientFactory> MONGO_CLIENT_FACTORY_MAP = new HashMap<>();

    /**
     * Constructor.
     * To prevent instantiation
     */
    private MongoDriverObjectFactory() {
        throw new AssertionError();
    }

    /**
     * Get the MongoClientFactory object.
     *
     * @param databaseUrl the database url
     * @return the MongoClientFactory
     */
    public static MongoClientFactory getMongoClient(String databaseUrl) {
        Objects.requireNonNull(databaseUrl);

        return MONGO_CLIENT_FACTORY_MAP.computeIfAbsent(databaseUrl, MongoClientFactory::new);
    }
}
