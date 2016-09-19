package vgalloy.riot.server.dao.internal.dao.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 */
public final class DaoFactory {

    private static final Map<Class<?>, Map<String, Map<String, Object>>> DAO_MAP = new HashMap<>();

    /**
     * Constructor.
     * To prevent instantiation
     */
    private DaoFactory() {
        throw new AssertionError();
    }

    /**
     * Return the dao with the correct database.
     *
     * @param daoClass     the dao class
     * @param databaseUrl  the database url
     * @param databaseName the database name
     * @param <T>          the dao
     * @return the dao
     */
    public static <T> T getDao(Class<T> daoClass, String databaseUrl, String databaseName) {
        Objects.requireNonNull(daoClass);
        Objects.requireNonNull(databaseUrl);
        Objects.requireNonNull(databaseName);

        Map<String, Map<String, Object>> databaseUrlMap = DAO_MAP.get(daoClass);
        databaseUrlMap = Optional.ofNullable(databaseUrlMap).orElse(new HashMap<>());
        DAO_MAP.put(daoClass, databaseUrlMap);

        Map<String, Object> daoMap = databaseUrlMap.get(databaseUrl);
        daoMap = Optional.ofNullable(daoMap).orElse(new HashMap<>());
        databaseUrlMap.put(databaseUrl, daoMap);

        T dao = (T) daoMap.get(databaseName);
        if (dao == null) {
            try {
                Constructor<T> constructor = daoClass.getConstructor(String.class, String.class);
                dao = constructor.newInstance(databaseUrl, databaseName);
            } catch (Exception e) {
                throw new RuntimeException("Can not instantiate dao", e);
            }
            daoMap.put(databaseName, dao);
        }
        return dao;
    }
}
