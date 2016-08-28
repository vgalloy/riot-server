package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import vgalloy.riot.api.api.dto.summoner.SummonerDto;
import vgalloy.riot.server.dao.api.dao.SummonerDao;
import vgalloy.riot.server.dao.internal.entity.dataobject.SummonerDo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class SummonerDaoImpl extends AbstractCommonDao<SummonerDto, SummonerDo> implements SummonerDao {

    public static final String COLLECTION_NAME = "summoner";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public SummonerDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }
}
