package vgalloy.riot.server.dao.internal.dao.factory;

import java.util.Objects;
import java.util.function.Supplier;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * Created by Vincent Galloy on 19/09/16.
 *
 * @author Vincent Galloy
 */
public final class DBCollectionFactory implements Supplier<DBCollection> {

    private final DBCollection dbCollection;

    /**
     * Constructor.
     *
     * @param db               the db
     * @param dbCollectionName the db collection name
     */
    DBCollectionFactory(DB db, String dbCollectionName) {
        Objects.requireNonNull(db);
        Objects.requireNonNull(dbCollectionName);

        dbCollection = db.getCollection(dbCollectionName);
    }

    @Override
    public DBCollection get() {
        return dbCollection;
    }
}
