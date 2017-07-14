package com.vgalloy.riot.server.dao.internal.dao.impl;

import vgalloy.riot.api.api.dto.matchlist.MatchReference;

import com.vgalloy.riot.server.dao.api.dao.MatchReferenceDao;
import com.vgalloy.riot.server.dao.internal.dao.AbstractDao;
import com.vgalloy.riot.server.dao.internal.entity.dpo.MatchReferenceDpo;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class MatchReferenceDaoImpl extends AbstractDao<MatchReference, MatchReferenceDpo> implements MatchReferenceDao {

    public static final String COLLECTION_NAME = "matchReference";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public MatchReferenceDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }
}
