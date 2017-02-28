package vgalloy.riot.server.service.internal.service.mapper;

import vgalloy.riot.server.service.api.model.game.Position;

/**
 * Created by Vincent Galloy on 23/08/16.
 *
 * @author Vincent Galloy
 */
public final class PositionMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private PositionMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a data object into a model.
     *
     * @param doPosition the position
     * @return the model
     */
    public static Position map(vgalloy.riot.server.dao.api.entity.Position doPosition) {
        return new Position(doPosition.getX(), doPosition.getY());
    }

    /**
     * Convert a dto object into a model.
     *
     * @param dtoPosition the position
     * @return the model
     */
    public static Position map(vgalloy.riot.api.api.dto.mach.Position dtoPosition) {
        return new Position(dtoPosition.getPositionX(), dtoPosition.getPositionY());
    }
}
