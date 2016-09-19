package vgalloy.riot.server.dao.internal.dao.factory;

import com.mongodb.DB;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Vincent Galloy - 19/09/16
 *         Created by Vincent Galloy on 19/09/16.
 */
public final class MongoDBFactory {

    private static final Map<String, Map<String, DB>> DB_MAP = new HashMap<>();

    /**
     * Constructor.
     * To prevent instantiation
     */
    private MongoDBFactory() {
        throw new AssertionError();
    }

    /**
     * Get a DB object.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     * @return the DB object
     */
    public static DB getDB(String databaseUrl, String databaseName) {
        Objects.requireNonNull(databaseUrl, "database url can not be null");
        Objects.requireNonNull(databaseName, "database name can not be null");

        Map<String, DB> dbMap = DB_MAP.get(databaseUrl);
        dbMap = Optional.ofNullable(dbMap).orElse(new HashMap<>());
        DB_MAP.put(databaseUrl, dbMap);

        DB db = dbMap.get(databaseName);
        db = Optional.ofNullable(db).orElse(MongoClientFactory.get(databaseUrl).getDB(databaseName));
        dbMap.put(databaseName, db);
        return db;
    }
}
