package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import vgalloy.riot.api.rest.request.mach.dto.MatchDetail;
import vgalloy.riot.server.dao.internal.entity.dataobject.MatchDetailDo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class MatchDetailDaoImpl extends AbstractCommonDao<MatchDetail, MatchDetailDo> {

    public static final String COLLECTION_NAME = "matchDetail";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public MatchDetailDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }
}
