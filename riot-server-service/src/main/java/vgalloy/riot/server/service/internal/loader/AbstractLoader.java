package vgalloy.riot.server.service.internal.loader;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.service.RiotApi;
import vgalloy.riot.server.service.api.model.LoaderInformation;
import vgalloy.riot.server.service.internal.executor.Executor;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 06/06/16.
 */
public abstract class AbstractLoader implements Loader {

    private final Logger defaultLogger = LoggerFactory.getLogger(getClass());

    protected final LoaderInformation loaderInformation = new LoaderInformation();
    protected final RiotApi riotApi;
    protected final Executor executor;

    /**
     * Constructor.
     *
     * @param riotApi  the riot api
     * @param executor the executor
     */
    protected AbstractLoader(RiotApi riotApi, Executor executor) {
        this.riotApi = Objects.requireNonNull(riotApi);
        this.executor = Objects.requireNonNull(executor);
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (Throwable e) {
            defaultLogger.error("{}", e.getMessage(), e);
        }
        loaderInformation.finish();
    }

    /**
     * Execute the loader.
     */
    public abstract void execute();

    @Override
    public LoaderInformation getLoaderInformation() {
        return loaderInformation;
    }
}
