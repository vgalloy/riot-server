package vgalloy.riot.server.service.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.service.api.model.GameInformation;
import vgalloy.riot.server.service.api.model.Model;
import vgalloy.riot.server.service.api.model.PlayerTimeline;
import vgalloy.riot.server.service.api.service.MatchDetailService;
import vgalloy.riot.server.service.internal.service.mapper.GameInformationMapper;
import vgalloy.riot.server.service.internal.service.mapper.HistoryMapper;
import vgalloy.riot.server.service.internal.service.mapper.ModelMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 23/08/16.
 */
@Component
public final class MatchDetailServiceImpl implements MatchDetailService {

    @Autowired
    private MatchDetailDao matchDetailDao;

    @Override
    public Optional<Model<MatchDetail>> get(Region region, Long itemId, LocalDate matchDate) {
        Optional<Entity<MatchDetail>> result = matchDetailDao.get(region, itemId, matchDate);
        if (result.isPresent()) {
            return Optional.of(ModelMapper.map(result.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<PlayerTimeline>> getTimeLines(Region region, long matchId, LocalDate localDate) {
        Optional<Model<MatchDetail>> optional = get(region, matchId, localDate);
        if (optional.isPresent()) {
            MatchDetail matchDetail = optional.get().getItem();
            return Optional.of(HistoryMapper.map(matchDetail));
        }
        return Optional.empty();
    }

    @Override
    public List<GameInformation> getMatchInformation(Region region, long summonerId, LocalDate localDate) {
        return matchDetailDao.findMatchDetailBySummonerId(region, summonerId, LocalDate.now().minus(30, ChronoUnit.DAYS), LocalDate.now().plus(1, ChronoUnit.DAYS)).stream()
                .sorted((o1, o2) -> (int) (o1.getMatchCreation() - o2.getMatchCreation()))
                .map(GameInformationMapper::map)
                .collect(Collectors.toList());
    }
}
