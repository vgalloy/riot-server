package vgalloy.riot.server.webservice.api.controller;

import vgalloy.riot.server.webservice.api.dto.VersionDto;

/**
 * Created by Vincent Galloy on 18/06/16.
 *
 * @author Vincent Galloy
 */
public interface HomeController {

    /**
     * Get the home page.
     *
     * @return Some information
     */
    VersionDto getHome();
}
