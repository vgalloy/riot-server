package vgalloy.riot.server.service.internal.service.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.service.api.model.Model;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
public final class ModelMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ModelMapper() {
        throw new AssertionError();
    }

    /**
     * Convert an entity into a model.
     *
     * @param entity the entity
     * @param <DTO>  the DTO type
     * @return the model
     */
    public static <DTO> Model<DTO> map(Entity<CommonWrapper<DTO>> entity) {
        Objects.requireNonNull(entity);

        Model<DTO> result = new Model<>(entity.getItemWrapper().getItemId().getRegion(), entity.getItemWrapper().getItemId().getId(), LocalDateTime.ofEpochSecond(entity.getLastUpdate(), 0, ZoneOffset.UTC));
        entity.getItemWrapper().getItem().ifPresent(result::setItem);
        return result;
    }
}
