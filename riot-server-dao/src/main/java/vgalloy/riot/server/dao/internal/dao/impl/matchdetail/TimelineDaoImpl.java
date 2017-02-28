package vgalloy.riot.server.dao.internal.dao.impl.matchdetail;

import vgalloy.riot.api.api.dto.mach.Timeline;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.internal.dao.AbstractDao;
import vgalloy.riot.server.dao.internal.dao.TimelineDao;
import vgalloy.riot.server.dao.internal.entity.dpo.TimelineDpo;
import vgalloy.riot.server.dao.internal.entity.mapper.DpoIdMapper;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class TimelineDaoImpl extends AbstractDao<Timeline, TimelineDpo> implements TimelineDao {

    public static final String COLLECTION_NAME = "timeline";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public TimelineDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }

    @Override
    public void remove(MatchDetailId matchDetailId) {
        this.collection.removeById(DpoIdMapper.toNormalizeString(matchDetailId));
    }
}
