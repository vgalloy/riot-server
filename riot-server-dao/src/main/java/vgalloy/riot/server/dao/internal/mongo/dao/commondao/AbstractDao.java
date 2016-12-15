package vgalloy.riot.server.dao.internal.mongo.dao.commondao;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.Optional;

import com.mongodb.DBCollection;
import org.mongojack.JacksonDBCollection;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonDpoWrapper;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.GenericMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.entity.dataobject.AbstractDataPersitenceObject;
import vgalloy.riot.server.dao.internal.mongo.entity.mapper.DataObjectMapper;
import vgalloy.riot.server.dao.internal.mongo.entity.mapper.DpoIdMapper;
import vgalloy.riot.server.dao.internal.mongo.exception.MongoDaoException;
import vgalloy.riot.server.dao.internal.mongo.factory.MongoDriverObjectFactory;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public abstract class AbstractDao<DTO, DATA_OBJECT extends AbstractDataPersitenceObject<DTO>> implements CommonDao<DTO> {

    protected final JacksonDBCollection<DATA_OBJECT, String> collection;
    private final GenericDao<DTO, DATA_OBJECT> genericDao;
    private final Class<DATA_OBJECT> dataObjectClass;

    /**
     * Constructor.
     *
     * @param databaseUrl    the database url
     * @param databaseName   the database name
     * @param collectionName the collection name
     */
    protected AbstractDao(String databaseUrl, String databaseName, String collectionName) {
        Objects.requireNonNull(databaseUrl);
        Objects.requireNonNull(databaseName);
        Objects.requireNonNull(collectionName);

        dataObjectClass = (Class<DATA_OBJECT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Objects.requireNonNull(dataObjectClass);
        DBCollection dbCollection = MongoDriverObjectFactory.getMongoClient(databaseUrl)
                .getDB(databaseName)
                .getDBCollection(collectionName)
                .get();
        collection = JacksonDBCollection.wrap(dbCollection, dataObjectClass, String.class);
        genericDao = new GenericMongoDaoImpl<>(collection);
    }

    @Override
    public void save(CommonDpoWrapper<DTO> itemWrapper) {
        Objects.requireNonNull(itemWrapper);

        try {
            Constructor<DATA_OBJECT> constructor = dataObjectClass.getConstructor(Region.class, Long.class);
            DATA_OBJECT dataObject = constructor.newInstance(itemWrapper.getItemId().getRegion(), itemWrapper.getItemId().getId());
            itemWrapper.getItem().ifPresent(dataObject::setItem);
            genericDao.update(dataObject);
        } catch (Exception e) {
            throw new MongoDaoException(MongoDaoException.UNABLE_TO_SAVE_THE_DTO, e);
        }
    }

    @Override
    public Optional<Entity<DTO, DpoId>> get(DpoId dpoId) {
        Objects.requireNonNull(dpoId);

        Optional<DATA_OBJECT> dataObject = genericDao.getById(DpoIdMapper.toNormalizeString(dpoId));
        if (dataObject.isPresent()) {
            return Optional.of(DataObjectMapper.mapToEntity(dataObject.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Entity<DTO, DpoId>> getRandom(Region region) {
        Objects.requireNonNull(region);

        Optional<DATA_OBJECT> dataObject = genericDao.getRandom(region);
        if (dataObject.isPresent()) {
            return Optional.of(DataObjectMapper.mapToEntity(dataObject.get()));
        }
        return Optional.empty();
    }
}
