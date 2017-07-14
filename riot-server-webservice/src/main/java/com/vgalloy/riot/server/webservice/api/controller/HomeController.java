package com.vgalloy.riot.server.webservice.api.controller;

import com.vgalloy.riot.server.webservice.api.dto.impl.VersionDto;

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
