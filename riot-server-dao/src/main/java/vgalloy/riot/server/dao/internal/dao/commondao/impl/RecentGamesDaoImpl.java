package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import vgalloy.riot.api.api.dto.game.RecentGamesDto;
import vgalloy.riot.server.dao.internal.dao.commondao.AbstractCommonDao;
import vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import vgalloy.riot.server.dao.internal.entity.dataobject.RecentGamesDo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class RecentGamesDaoImpl extends AbstractCommonDao<RecentGamesDto, RecentGamesDo> implements RecentGamesDao {

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
