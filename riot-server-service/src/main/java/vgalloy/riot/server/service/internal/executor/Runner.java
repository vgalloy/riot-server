package vgalloy.riot.server.service.internal.executor;

import java.util.List;

import vgalloy.riot.server.service.internal.loader.Loader;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public interface Runner {

    /**
     * Get the loaderList.
     *
     * @return the loaders as list
     */
    List<Loader> getLoaderList();
}
