package vgalloy.riot.server.service.internal.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public final class MatchDetailServiceImpl implements MatchDetailService {

    @Autowired
    private MatchDetailDao matchDetailDao;

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
