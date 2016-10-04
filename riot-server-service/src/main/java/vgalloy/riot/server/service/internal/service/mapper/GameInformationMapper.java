package vgalloy.riot.server.service.internal.service.mapper;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.service.api.model.GameInformation;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public final class GameInformationMapper {

    /**
     * Constructor.
     *
     * To prevent instantiation
     */
    private GameInformationMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a matchDetail into a GameInformation.
     *
     * @param matchDetail the match Detail
     * @return the GameInformation
     */
    public static GameInformation map(MatchDetail matchDetail) {
        return new GameInformation();
    }
}
