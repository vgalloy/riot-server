package vgalloy.riot.server.service.api.service;

import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.service.api.model.Model;

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
