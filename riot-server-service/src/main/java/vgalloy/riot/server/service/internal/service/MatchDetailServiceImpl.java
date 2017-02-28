package vgalloy.riot.server.service.internal.service;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.AbstractDpoWrapper;
import vgalloy.riot.server.service.api.model.game.Game;
import vgalloy.riot.server.service.api.model.game.GameId;
import vgalloy.riot.server.service.api.model.wrapper.ResourceWrapper;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.internal.service.mapper.GameMapper;

/**
 * Created by Vincent Galloy on 23/08/16.
 *
 * @author Vincent Galloy
 */
public final class MatchDetailServiceImpl implements MatchDetailService {

    private final MatchDetailDao matchDetailDao;

    /**
     * Constructor.
     *
     * @param matchDetailDao the match detail dao
     */
    public MatchDetailServiceImpl(MatchDetailDao matchDetailDao) {
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
    }

    @Override
    public ResourceWrapper<Game> get(GameId gameId) {
        Objects.requireNonNull(gameId);

        Optional<Entity<MatchDetail, MatchDetailId>> result = matchDetailDao.get(new MatchDetailId(gameId.getRegion(), gameId.getId(), gameId.getMatchDate()));
        return result.map(AbstractDpoWrapper::getItem)
                .map(e -> e.map(GameMapper::map)
                        .map(ResourceWrapper::of)
                        .orElseGet(ResourceWrapper::doesNotExist))
                .orElseGet(ResourceWrapper::notLoaded);
    }
}
