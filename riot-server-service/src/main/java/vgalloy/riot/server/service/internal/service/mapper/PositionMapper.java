package vgalloy.riot.server.service.internal.service.mapper;

import vgalloy.riot.server.service.api.model.Position;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
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
     * Convert an entity into a model.
     *
     * @param daoPosition the position
     * @return the model
     */
    public static Position map(vgalloy.riot.server.dao.api.entity.Position daoPosition) {
        return new Position(daoPosition.getX(), daoPosition.getY());
    }
}
