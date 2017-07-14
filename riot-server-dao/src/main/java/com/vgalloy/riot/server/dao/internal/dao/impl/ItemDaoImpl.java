package com.vgalloy.riot.server.dao.internal.dao.impl;

import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;

import com.vgalloy.riot.server.dao.api.dao.ItemDao;
import com.vgalloy.riot.server.dao.internal.dao.AbstractDao;
import com.vgalloy.riot.server.dao.internal.entity.dpo.ItemDpo;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class ItemDaoImpl extends AbstractDao<ItemDto, ItemDpo> implements ItemDao {

    public static final String COLLECTION_NAME = "item";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public ItemDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }
}
