package vgalloy.riot.server.dao.internal.dao.query.mapper;

import org.bson.Document;

import vgalloy.riot.server.dao.api.entity.Position;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public final class PositionMapper {

    /**
     * Constructor.
     * Prevent instantiation.
     */
    private PositionMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a document into a position.
     *
     * @param document the document
     * @return a position
     */
    public static Position map(Document document) {
        int x = (int) document.get("x");
        int y = (int) document.get("y");
        return new Position(x, y);
    }
}
