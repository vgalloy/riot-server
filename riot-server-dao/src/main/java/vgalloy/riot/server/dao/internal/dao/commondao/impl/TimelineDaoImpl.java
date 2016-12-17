package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import vgalloy.riot.api.api.dto.mach.Timeline;
import vgalloy.riot.server.dao.internal.dao.commondao.AbstractDao;
import vgalloy.riot.server.dao.internal.dao.commondao.TimelineDao;
import vgalloy.riot.server.dao.internal.entity.dpo.TimelineDpo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
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
}
