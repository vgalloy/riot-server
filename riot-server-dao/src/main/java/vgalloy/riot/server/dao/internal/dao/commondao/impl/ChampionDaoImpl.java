package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.internal.dao.commondao.AbstractDao;
import vgalloy.riot.server.dao.internal.entity.dpo.ChampionDpo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class ChampionDaoImpl extends AbstractDao<ChampionDto, ChampionDpo> implements ChampionDao {

    public static final String COLLECTION_NAME = "champion";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public ChampionDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }
}
