package vgalloy.riot.server.service.internal.loader;

import org.springframework.beans.factory.annotation.Autowired;
import vgalloy.riot.api.service.RiotApi;
import vgalloy.riot.server.service.internal.executor.Executor;
import vgalloy.riot.server.service.internal.executor.Runner;

import javax.annotation.PostConstruct;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 06/06/16.
 */
public abstract class AbstractLoader implements Loader {

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
}
