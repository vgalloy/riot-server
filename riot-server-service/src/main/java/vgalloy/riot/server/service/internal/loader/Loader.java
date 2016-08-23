package vgalloy.riot.server.service.internal.loader;

import vgalloy.riot.server.service.api.model.LoaderInformation;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 06/06/16.
 */
public interface Loader extends Runnable {

    /**
     * Get information about the loader.
     *
     * @return the information
     */
    LoaderInformation getLoaderInformation();
}
