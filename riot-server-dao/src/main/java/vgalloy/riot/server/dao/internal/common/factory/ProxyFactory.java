package vgalloy.riot.server.dao.internal.common.factory;

import java.lang.reflect.Proxy;
import java.util.Objects;

import vgalloy.riot.server.dao.internal.common.proxy.ProxyDao;
import vgalloy.riot.server.dao.internal.common.service.impl.ProxyServiceImpl;

/**
 * @author Vincent Galloy - 15/12/16
 *         Created by Vincent Galloy on 15/12/16.
 */
public final class ProxyFactory {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ProxyFactory() {
        throw new AssertionError();
    }

    /**
     * Create a proxy.
     *
     * @param clazz            the class of the dao
     * @param mongoDao         the mongo dao
     * @param elasticsearchDao the elasticsearch dao
     * @param <T>              the type
     * @return the dao proxy
     */
    public static <T> T create(Class<T> clazz, T mongoDao, T elasticsearchDao) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(mongoDao);
        Objects.requireNonNull(elasticsearchDao);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ProxyDao(ProxyServiceImpl.INSTANCE, mongoDao, elasticsearchDao));
    }
}
