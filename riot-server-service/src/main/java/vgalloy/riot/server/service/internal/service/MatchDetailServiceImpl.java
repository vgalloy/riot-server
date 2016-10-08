package vgalloy.riot.server.service.internal.service;

import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.service.api.model.Game;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.internal.service.mapper.GameMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
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
    public Optional<Game> get(MatchDetailId matchDetailId) {
        Objects.requireNonNull(matchDetailId);

        Optional<Entity<MatchDetailWrapper>> result = matchDetailDao.get(matchDetailId);
        if (result.isPresent()) {
            return Optional.of(GameMapper.map(result.get()));
        }
        return Optional.empty();
    }
}
