package vgalloy.riot.server.service.internal.factory;

import vgalloy.riot.server.dao.api.factory.MongoDaoFactory;
import vgalloy.riot.server.service.api.context.ContextManager;
import vgalloy.riot.server.service.internal.loader.context.impl.ContextManagerImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class ContextFactory {

    private static final ContextManager CONTEXT_MANAGER = new ContextManagerImpl(ExecutorFactory.getRiotApi(), ExecutorFactory.getExecutor(), MongoDaoFactory.getSummonerDao(), BrokerFactory.getSummonerLoaderClient());

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ContextFactory() {
        throw new AssertionError();
    }

    public static ContextManager getContextManager() {
        return CONTEXT_MANAGER;
    }
}
