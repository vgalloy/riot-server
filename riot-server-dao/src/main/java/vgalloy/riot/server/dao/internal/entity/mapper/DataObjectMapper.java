package vgalloy.riot.server.dao.internal.entity.mapper;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.internal.entity.dataobject.DataObject;

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
    public static <DTO> Entity<CommonWrapper<DTO>> mapToEntity(DataObject<DTO> dataObject) {
        Objects.requireNonNull(dataObject);

        CommonWrapper<DTO> wrapper = mapToWrapper(dataObject);
        return new Entity<>(wrapper, dataObject.getLastUpdate());
    }

    /**
     * Converts a database object into an wrapper.
     *
     * @param dataObject the database object
     * @param <DTO>      the dto type
     * @return the common wrapper
     */
    private static <DTO> CommonWrapper<DTO> mapToWrapper(DataObject<DTO> dataObject) {
        Objects.requireNonNull(dataObject);

        ItemId itemId = ItemIdMapper.fromNormalize(dataObject.getId());
        if (dataObject.getItem() == null) {
            return new CommonWrapper<>(itemId);
        }
        return new CommonWrapper<>(itemId, dataObject.getItem());
    }
}
