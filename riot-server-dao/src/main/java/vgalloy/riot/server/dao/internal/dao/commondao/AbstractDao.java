package vgalloy.riot.server.dao.internal.dao.commondao;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.Optional;

import com.mongodb.DBCollection;
import org.mongojack.JacksonDBCollection;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.server.dao.api.dao.CommonDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.itemid.ItemId;
import vgalloy.riot.server.dao.api.entity.wrapper.CommonWrapper;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.GenericDaoImpl;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import vgalloy.riot.server.dao.internal.entity.dataobject.AbstractDataObject;
import vgalloy.riot.server.dao.internal.entity.mapper.DataObjectMapper;
import vgalloy.riot.server.dao.internal.entity.mapper.ItemIdMapper;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public abstract class AbstractDao<DTO, DATA_OBJECT extends AbstractDataObject<DTO>> implements CommonDao<DTO> {

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
        genericDao = new GenericDaoImpl<>(collection);
    }

    @Override
    public void save(CommonWrapper<DTO> itemWrapper) {
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
    public Optional<Entity<CommonWrapper<DTO>>> get(ItemId itemId) {
        Objects.requireNonNull(itemId);

        Optional<DATA_OBJECT> dataObject = genericDao.getById(ItemIdMapper.toNormalizeString(itemId));
        if (dataObject.isPresent()) {
            return Optional.of(DataObjectMapper.mapToEntity(dataObject.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Entity<CommonWrapper<DTO>>> getRandom(Region region) {
        Objects.requireNonNull(region);

        Optional<DATA_OBJECT> dataObject = genericDao.getRandom(region);
        if (dataObject.isPresent()) {
            return Optional.of(DataObjectMapper.mapToEntity(dataObject.get()));
        }
        return Optional.empty();
    }
}
