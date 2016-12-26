package vgalloy.riot.server.dao.internal.dao.impl.matchdetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.api.api.dto.mach.Timeline;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.dao.api.mapper.MatchDetailIdMapper;
import vgalloy.riot.server.dao.internal.dao.TimelineDao;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class GlobalMatchDetailDaoImpl implements MatchDetailDao {

    private final TimelineDao timelineDao;
    private final MatchDetailDao matchDetailDao;

    /**
     * Constructor.
     *
     * @param timelineDao    the timeline dao
     * @param matchDetailDao the match detail dao
     */
    public GlobalMatchDetailDaoImpl(TimelineDao timelineDao, MatchDetailDao matchDetailDao) {
        this.timelineDao = Objects.requireNonNull(timelineDao);
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
    }

    @Override
    public void save(MatchDetailWrapper matchDetailWrapper) {
        Objects.requireNonNull(matchDetailWrapper);

        Optional<MatchDetail> optionalMatchDetail = matchDetailWrapper.getItem();
        if (optionalMatchDetail.isPresent()) {
            MatchDetail matchDetail = optionalMatchDetail.get();
            Timeline timeline = matchDetail.getTimeline();
            if (timeline != null) {
                timelineDao.save(new CommonDpoWrapper<>(matchDetailWrapper.getItemId(), timeline));
            }

            matchDetail.setTimeline(null);
            matchDetailDao.save(matchDetailWrapper);
            matchDetail.setTimeline(timeline);
        } else {
            matchDetailDao.save(matchDetailWrapper);
        }
    }

    @Override
    public Optional<Entity<MatchDetail, MatchDetailId>> get(MatchDetailId matchDetailId) {
        Objects.requireNonNull(matchDetailId);

        Optional<Entity<MatchDetail, MatchDetailId>> optional = matchDetailDao.get(matchDetailId);
        if (optional.isPresent() && optional.get().getItem().isPresent()) {
            Timeline timeline = getTimeline(matchDetailId);
            optional.get().getItem().get().setTimeline(timeline);
        }
        return optional;
    }

    @Override
    public List<MatchDetail> findMatchDetailBySummonerId(Region region, long summonerId, LocalDate from, LocalDate to) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        List<MatchDetail> resultWithoutTimeline = matchDetailDao.findMatchDetailBySummonerId(region, summonerId, from, to);

        for (MatchDetail matchDetail : resultWithoutTimeline) {
            MatchDetailId matchDetailId = MatchDetailIdMapper.map(matchDetail);
            Timeline timeline = getTimeline(matchDetailId);
            matchDetail.setTimeline(timeline);
        }
        return resultWithoutTimeline;
    }

    /**
     * Get the timeline from the matchDetailId.
     *
     * @param matchDetailId the matchDetailId
     * @return the timeline can be null
     */
    private Timeline getTimeline(MatchDetailId matchDetailId) {
        Optional<Entity<Timeline, DpoId>> timelineOptional = timelineDao.get(matchDetailId);
        if (timelineOptional.isPresent() && timelineOptional.get().getItem().isPresent()) {
            return timelineOptional.get().getItem().get();
        }
        return null;
    }
}
