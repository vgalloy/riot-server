package vgalloy.riot.server.service.internal.loader.context.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vgalloy.riot.api.api.model.RiotApi;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.service.api.context.ContextManager;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.loader.Loader;
import vgalloy.riot.server.service.internal.loader.client.SummonerLoaderClient;
import vgalloy.riot.server.service.internal.loader.impl.intializer.LoaderInitializer;
import vgalloy.riot.server.service.internal.loader.impl.matchdetail.PrivilegedLoader;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
public final class ContextManagerImpl implements ContextManager {

    private final List<Loader> loaderList = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param riotApi              the riot api
     * @param executor             the executor
     * @param summonerDao          the summoner dao
     * @param summonerLoaderClient the summoner loader client
     */
    public ContextManagerImpl(RiotApi riotApi,
                              Executor executor,
                              SummonerDao summonerDao,
                              SummonerLoaderClient summonerLoaderClient) {

        register(new LoaderInitializer(riotApi, executor, summonerDao));
        new Thread(new PrivilegedLoader(summonerLoaderClient)).start();
    }

    /**
     * Register the loader into the runner and make it start.
     *
     * @param loader the loader
     */
    private void register(Loader loader) {
        loaderList.add(loader);
        new Thread(loader).start();
    }

    @Override
    public List<Loader> getLoaderList() {
        return Collections.unmodifiableList(loaderList);
    }
}
