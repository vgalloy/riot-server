package vgalloy.riot.server.service.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.model.PlayerTimeline;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.internal.service.mapper.HistoryMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Component
public class MatchDetailServiceImpl extends AbstractService<MatchDetail> implements MatchDetailService {

    @Autowired
    private MatchDetailDao matchDetailDao;

    @Override
    public Optional<List<PlayerTimeline>> getTimeLines(Region region, long matchId) {
        Optional<Entity<MatchDetail>> optional = matchDetailDao.get(region, matchId);
        if (optional.isPresent()) {
            MatchDetail matchDetail = optional.get().getItem();
            if (matchDetail != null) {
                return Optional.of(HistoryMapper.map(matchDetail));
            }
        }
        return Optional.empty();
    }
}
