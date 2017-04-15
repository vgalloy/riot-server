package vgalloy.riot.server.dao.internal.dao.impl;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.internal.dao.GenericDao;
import vgalloy.riot.server.dao.internal.entity.dpo.AbstractDpo;

/**
 * Created by Vincent Galloy on 09/12/15.
 *
 * @author Vincent Galloy
 */
public final class GenericDaoImpl<DTO, DATA_OBJECT extends AbstractDpo<DTO>> implements GenericDao<DTO, DATA_OBJECT> {

    private static final Random RANDOM = new SecureRandom();
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
        Objects.requireNonNull(region);
        int max = collection
            .find(DBQuery.is("region", region))
            .count();
        if (max == 0) {
            return Optional.empty();
        }
        int rand = Math.abs(RANDOM.nextInt() % max);
        return Optional.of(collection
            .find(DBQuery.is("region", region))
            .limit(-1)
            .skip(rand)
            .next());
    }
}
