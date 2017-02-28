package vgalloy.riot.server.dao.internal.entity.mapper;

import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.internal.entity.dpo.AbstractDpo;

/**
 * Created by Vincent Galloy on 12/07/16.
 *
 * @author Vincent Galloy
 */
public final class DpoMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private DpoMapper() {
        throw new AssertionError();
    }

    /**
     * Converts a database object into an entity.
     *
     * @param dataObject the database object
     * @param <DTO>      the dto type
     * @return the entity
     */
    public static <DTO> Entity<DTO, DpoId> mapToEntity(AbstractDpo<DTO> dataObject) {
        Objects.requireNonNull(dataObject);

        DpoId dpoId = DpoIdMapper.fromNormalize(dataObject.getId());
        return new Entity<>(dpoId, dataObject.getItem(), dataObject.getLastUpdate());
    }
}
