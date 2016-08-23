package vgalloy.riot.server.service.api.service;

import vgalloy.riot.server.service.api.model.LoaderInformation;

import java.util.Map;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/07/16.
 */
public interface InformationService {

    /**
     * Get the globalInformation about loader.
     *
     * @return a map with the id of each loader
     */
    Map<Integer, String> getGlobalInformation();

    /**
     * Get more information about one loader.
     *
     * @param loaderId the loader id given by {@link #getGlobalInformation()}
     * @return the detail of one loader
     */
    LoaderInformation getInformation(int loaderId);
}
