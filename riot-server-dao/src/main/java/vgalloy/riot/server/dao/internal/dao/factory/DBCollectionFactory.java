package vgalloy.riot.server.dao.internal.dao.factory;

import java.util.Objects;
import java.util.function.Supplier;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * @author Vincent Galloy - 19/09/16
 *         Created by Vincent Galloy on 19/09/16.
 */
public final class DBCollectionFactory implements Supplier<DBCollection> {

    private final DBCollection dbCollection;

    /**
     * Constructor.
     *
     * @param db               the db
     * @param dbCollectionName the db collection name
     */
    public DBCollectionFactory(DB db, String dbCollectionName) {
        Objects.requireNonNull(db);
        Objects.requireNonNull(dbCollectionName);

        dbCollection = db.getCollection(dbCollectionName);
    }

    @Override
    public DBCollection get() {
        return dbCollection;
    }
}
