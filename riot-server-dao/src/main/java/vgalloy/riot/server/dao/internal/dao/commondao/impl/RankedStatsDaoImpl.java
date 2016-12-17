package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import vgalloy.riot.api.api.dto.stats.RankedStatsDto;
import vgalloy.riot.server.dao.api.dao.RankedStatsDao;
import vgalloy.riot.server.dao.internal.dao.commondao.AbstractDao;
import vgalloy.riot.server.dao.internal.entity.dpo.RankedStatsDpo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class RankedStatsDaoImpl extends AbstractDao<RankedStatsDto, RankedStatsDpo> implements RankedStatsDao {

    public static final String COLLECTION_NAME = "rankedStats";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public RankedStatsDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }
}
