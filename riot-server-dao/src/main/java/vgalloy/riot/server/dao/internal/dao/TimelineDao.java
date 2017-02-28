package vgalloy.riot.server.dao.internal.dao;

import vgalloy.riot.api.api.dto.mach.Timeline;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;

/**
 * Created by Vincent Galloy on 17/12/16.
 *
 * @author Vincent Galloy
 */
public interface TimelineDao extends CommonDao<Timeline> {

    /**
     * Remove the timeline.
     *
     * @param matchDetailId the match detail id
     */
    void remove(MatchDetailId matchDetailId);
}
