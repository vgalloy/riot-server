package vgalloy.riot.server.service.internal.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vgalloy.riot.api.service.RiotApi;
import vgalloy.riot.server.service.api.model.LoaderInformation;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.executor.Runner;

import javax.annotation.PostConstruct;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 06/06/16.
 */
public abstract class AbstractLoader implements Loader {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLoader.class);

    protected final LoaderInformation loaderInformation = new LoaderInformation();
    @Autowired
    protected RiotApi riotApi;
    @Autowired
    protected Executor executor;
    @Autowired
    private Runner runner;

    /**
     * Register and start the runner.
     */
    @PostConstruct
    public void postConstruct() {
        runner.register(this);
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (Throwable e) {
            LOGGER.error("{}", e.getMessage(), e);
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
