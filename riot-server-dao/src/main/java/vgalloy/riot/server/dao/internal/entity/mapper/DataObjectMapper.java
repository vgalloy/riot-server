package vgalloy.riot.server.dao.internal.entity.mapper;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.internal.entity.dataobject.AbstractDataObject;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 12/07/16.
 */
public final class DataObjectMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private DataObjectMapper() {
        throw new AssertionError();
    }

    /**
     * Converts a database object into an entity.
     *
     * @param dataObject the database object
     * @param <DTO>      the dto type
     * @return the entity
     */
    public static <DTO> Entity<DTO, ItemId> mapToEntity(AbstractDataObject<DTO> dataObject) {
        Objects.requireNonNull(dataObject);

        ItemId itemId = ItemIdMapper.fromNormalize(dataObject.getId());
        if (dataObject.getItem() == null) {
            return new Entity<>(itemId, dataObject.getLastUpdate());
        }
        return new Entity<>(itemId, dataObject.getItem(), dataObject.getLastUpdate());
    }
}
