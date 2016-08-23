package vgalloy.riot.server.service.internal.executor.impl;

import org.springframework.stereotype.Component;
import vgalloy.riot.server.service.internal.executor.Runner;
import vgalloy.riot.server.service.internal.loader.Loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
@Component
public class RunnerImpl implements Runner {

    private final List<Loader> loaderList = new ArrayList<>();

    @Override
    public void register(Loader loader) {
        loaderList.add(loader);
        new Thread(loader).start();
    }

    @Override
    public List<Loader> getLoaderList() {
        return Collections.unmodifiableList(loaderList);
    }
}
