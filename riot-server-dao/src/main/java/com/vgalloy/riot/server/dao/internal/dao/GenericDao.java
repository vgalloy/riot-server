package com.vgalloy.riot.server.dao.internal.dao;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;

import com.vgalloy.riot.server.dao.internal.entity.dpo.AbstractDpo;

/**
 * Created by Vincent Galloy on 02/12/15.
 *
 * @author Vincent Galloy
 */
public interface GenericDao<DTO, DPO extends AbstractDpo<DTO>> {

    /**
     * Find the element with the given id. Return null if no element found.
     *
     * @param id The id
     * @return L'object with the given id
     */
    Optional<DPO> getById(String id);

    /**
     * Update an existing element.
     *
     * @param t The element to update
     * @return The modify object
     */
    DPO update(DPO t);

    /**
     * Get one random element in the collection.
     *
     * @param region the region
     * @return the random element
     */
    Optional<DPO> getRandom(Region region);
}
