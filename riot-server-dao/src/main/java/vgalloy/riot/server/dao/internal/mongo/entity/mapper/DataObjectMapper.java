package vgalloy.riot.server.dao.internal.mongo.entity.mapper;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.internal.mongo.entity.dataobject.AbstractDataPersitenceObject;

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
    public static <DTO> Entity<DTO, DpoId> mapToEntity(AbstractDataPersitenceObject<DTO> dataObject) {
        Objects.requireNonNull(dataObject);

        DpoId dpoId = DpoIdMapper.fromNormalize(dataObject.getId());
        if (dataObject.getItem() == null) {
            return new Entity<>(dpoId, dataObject.getLastUpdate());
        }
        return new Entity<>(dpoId, dataObject.getItem(), dataObject.getLastUpdate());
    }
}
