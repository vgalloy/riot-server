package vgalloy.riot.server.dao.internal.dao.impl;

import vgalloy.riot.api.api.dto.lolstaticdata.ItemDto;
import vgalloy.riot.server.dao.api.dao.ItemDao;
import vgalloy.riot.server.dao.internal.dao.AbstractDao;
import vgalloy.riot.server.dao.internal.entity.dpo.ItemDpo;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
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
