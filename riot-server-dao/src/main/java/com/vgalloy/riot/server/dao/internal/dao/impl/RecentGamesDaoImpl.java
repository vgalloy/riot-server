package com.vgalloy.riot.server.dao.internal.dao.impl;

import vgalloy.riot.api.api.dto.game.RecentGamesDto;

import com.vgalloy.riot.server.dao.api.dao.RecentGamesDao;
import com.vgalloy.riot.server.dao.internal.dao.AbstractDao;
import com.vgalloy.riot.server.dao.internal.entity.dpo.RecentGamesDpo;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class RecentGamesDaoImpl extends AbstractDao<RecentGamesDto, RecentGamesDpo> implements RecentGamesDao {

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
