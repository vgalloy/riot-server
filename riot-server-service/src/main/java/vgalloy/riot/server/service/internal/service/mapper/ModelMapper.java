package vgalloy.riot.server.service.internal.service.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import vgalloy.riot.server.dao.api.entity.Entity;
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
    public static <DTO> Model<DTO> map(Entity<DTO> entity) {
        return new Model<>(entity.getRegion(), entity.getItemId(), entity.getItem(), LocalDateTime.ofEpochSecond(entity.getLastUpdate(), 0, ZoneOffset.UTC));
    }
}
