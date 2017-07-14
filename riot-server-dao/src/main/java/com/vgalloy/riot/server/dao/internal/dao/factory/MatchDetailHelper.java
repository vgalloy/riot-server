package com.vgalloy.riot.server.dao.internal.dao.factory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.vgalloy.riot.server.dao.internal.dao.impl.matchdetail.MatchDetailDaoImpl;
import com.vgalloy.riot.server.dao.internal.exception.MongoDaoException;

/**
 * Created by Vincent Galloy on 26/12/16.
 *
 * @author Vincent Galloy
 */
public final class MatchDetailHelper {

    private static final String COLLECTION_NAME_PATTERN = "yyyy_MM_dd";

    /**
     * Constructor.
     * To prevent instantiation
     */
    private MatchDetailHelper() {
        throw new AssertionError();
    }

    /**
     * Convert a local date into a collection name. And check the date aren't in the future.
     *
     * @param localDate the local date
     * @return the collection name
     */
    public static String getCollectionName(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        if (localDate.isBefore(LocalDate.now().minus(4, ChronoUnit.YEARS))) {
            throw new MongoDaoException("the date " + localDate + " is to old");
        }
        if (localDate.isAfter(LocalDate.now().plus(1, ChronoUnit.DAYS))) {
            throw new MongoDaoException("the date " + localDate + " is in the future");
        }
        return MatchDetailDaoImpl.COLLECTION_NAME + "_" + localDate.format(DateTimeFormatter.ofPattern(COLLECTION_NAME_PATTERN));
    }
}
