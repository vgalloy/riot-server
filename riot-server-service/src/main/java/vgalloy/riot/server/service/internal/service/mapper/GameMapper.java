package vgalloy.riot.server.service.internal.service.mapper;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.service.api.model.Game;
import vgalloy.riot.server.service.api.model.GameInformation;

/**
 * @author Vincent Galloy - 07/10/16
 *         Created by Vincent Galloy on 07/10/16.
 */
public final class GameMapper {

    /**
     * Constructor.
     * <p>
     * To prevent instantiation
     */
    private GameMapper() {
        throw new AssertionError();
    }

    /**
     * Convert a entity into a Game.
     *
     * @param entity the match detail entity
     * @return the Game
     */
    public static Game map(Entity<MatchDetailWrapper> entity) {
        Objects.requireNonNull(entity);

        long lastUpdate = entity.getLastUpdate();
        MatchDetailWrapper matchDetailWrapper = entity.getItemWrapper();
        String matchId = MatchDetailIdMapper.map(matchDetailWrapper.getItemId());

        Optional<MatchDetail> matchDetailOptional = matchDetailWrapper.getItem();
        if (!matchDetailOptional.isPresent()) {
            return new Game(matchId, lastUpdate);
        }
        GameInformation gameInformation = GameInformationMapper.map(matchDetailOptional.get());
        return new Game(matchId, lastUpdate, gameInformation);
    }
}