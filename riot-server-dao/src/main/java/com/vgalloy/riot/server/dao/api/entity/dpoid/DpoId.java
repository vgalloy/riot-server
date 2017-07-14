package com.vgalloy.riot.server.dao.api.entity.dpoid;

import java.io.Serializable;

import vgalloy.riot.api.api.constant.Region;

/**
 * Created by Vincent Galloy on 07/10/16.
 *
 * @author Vincent Galloy
 */
public interface DpoId extends Serializable {

    /**
     * Get the region of the item.
     *
     * @return the region
     */
    Region getRegion();

    /**
     * Get the id of the item.
     *
     * @return the id
     */
    Long getId();
}
