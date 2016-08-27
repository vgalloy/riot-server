package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import vgalloy.riot.api.rest.request.game.dto.RecentGamesDto;
import vgalloy.riot.server.dao.internal.entity.dataobject.RecentGamesDo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class RecentGamesDaoImpl extends AbstractCommonDao<RecentGamesDto, RecentGamesDo> {

    public static final String COLLECTION_NAME = "recentGames";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public RecentGamesDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }
}
