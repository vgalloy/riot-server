package vgalloy.riot.server.service.api.service;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.Model;

import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public interface CommonService<DTO> {

    /**
     * Get the model.
     *
     * @param region the region
     * @param itemId the item id
     * @return the datable
     */
    Optional<Model<DTO>> get(Region region, Long itemId);
}
