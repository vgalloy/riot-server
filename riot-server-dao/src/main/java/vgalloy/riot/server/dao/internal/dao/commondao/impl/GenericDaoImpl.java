package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.internal.dao.commondao.GenericDao;
import vgalloy.riot.server.dao.internal.entity.dataobject.AbstractDataObject;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/12/15.
 */
public final class GenericDaoImpl<DTO, DATA_OBJECT extends AbstractDataObject<DTO>> implements GenericDao<DTO, DATA_OBJECT> {

    public static final String REGION_CAN_NOT_BE_NULL = "region can not be null";

    private final JacksonDBCollection<DATA_OBJECT, String> collection;

    /**
     * Constructor with the collectionFactory.
     *
     * @param collection the mongo jack collection
     */
    public GenericDaoImpl(JacksonDBCollection<DATA_OBJECT, String> collection) {
        this.collection = Objects.requireNonNull(collection);
    }

    @Override
    public Optional<DATA_OBJECT> getById(String id) {
        return Optional.ofNullable(collection.findOneById(id));
    }

    @Override
    public DATA_OBJECT update(DATA_OBJECT t) {
        collection.save(t);
        return t;
    }

    @Override
    public Optional<DATA_OBJECT> getRandom(Region region) {
        Objects.requireNonNull(region, REGION_CAN_NOT_BE_NULL);
        int max = collection
                .find(DBQuery.is("region", region))
                .count();
        if (max == 0) {
            return Optional.empty();
        }
        Random random = new SecureRandom();
        int rand = Math.abs(random.nextInt()) % max;
        return Optional.of(collection
                .find(DBQuery.is("region", region))
                .limit(-1)
                .skip(rand)
                .next());
    }
}
