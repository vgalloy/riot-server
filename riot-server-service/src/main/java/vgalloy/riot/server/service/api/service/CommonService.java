package vgalloy.riot.server.service.api.service;

import java.util.Optional;

import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.service.api.model.Model;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public interface CommonService<DTO> {

    /**
     * Get the model.
     *
     * @param dpoId the item id
     * @return the datable
     */
    Optional<Model<DTO>> get(DpoId dpoId);
}
