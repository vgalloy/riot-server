package vgalloy.riot.server.dao.internal.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import vgalloy.riot.server.dao.internal.dao.impl.GenericDaoImpl;
import vgalloy.riot.server.dao.internal.entity.dpo.AbstractDpo;
import vgalloy.riot.server.dao.internal.entity.mapper.DpoIdMapper;
import vgalloy.riot.server.dao.internal.entity.mapper.DpoMapper;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;

/**
 * Created by Vincent Galloy on 07/07/16.
 *
 * @author Vincent Galloy
 */
public abstract class AbstractDao<DTO, DPO extends AbstractDpo<DTO>> implements CommonDao<DTO> {

    protected final JacksonDBCollection<DPO, String> collection;
    private final GenericDao<DTO, DPO> genericDao;
    private final Class<DPO> dataObjectClass;
    private final String databaseUrl;
    private final String databaseName;

    /**
     * Constructor.
     *
     * @param databaseUrl    the database url
     * @param databaseName   the database name
     * @param collectionName the collection name
     */
    @SuppressWarnings("unchecked")
    protected AbstractDao(String databaseUrl, String databaseName, String collectionName) {
        Objects.requireNonNull(collectionName);
        this.databaseUrl = Objects.requireNonNull(databaseUrl);
        this.databaseName = Objects.requireNonNull(databaseName);
        this.dataObjectClass = (Class<DPO>) ParameterizedType.class.cast(getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        DBCollection dbCollection = MongoDriverObjectFactory.getMongoClient(databaseUrl)
                .getDB(databaseName)
                .getDBCollection(collectionName)
                .get();
        collection = JacksonDBCollection.wrap(dbCollection, dataObjectClass, String.class);
        genericDao = new GenericDaoImpl<>(collection);
    }

    @Override
    public void save(CommonDpoWrapper<DTO> itemWrapper) {
        Objects.requireNonNull(itemWrapper);

        try {
            Constructor<DPO> constructor = dataObjectClass.getConstructor(Region.class, Long.class);
            DPO dataObject = constructor.newInstance(itemWrapper.getItemId().getRegion(), itemWrapper.getItemId().getId());
            itemWrapper.getItem().ifPresent(dataObject::setItem);
            genericDao.update(dataObject);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new MongoDaoException(MongoDaoException.UNABLE_TO_SAVE_THE_DTO, e);
        }
    }

    @Override
    public Optional<Entity<DTO, DpoId>> get(DpoId dpoId) {
        Objects.requireNonNull(dpoId);

        return genericDao.getById(DpoIdMapper.toNormalizedString(dpoId))
                .map(DpoMapper::mapToEntity);
    }

    @Override
    public Optional<Entity<DTO, DpoId>> getRandom(Region region) {
        Objects.requireNonNull(region);

        return genericDao.getRandom(region)
                .map(DpoMapper::mapToEntity);
    }

    protected String getDatabaseUrl() {
        return databaseUrl;
    }

    protected String getDatabaseName() {
        return databaseName;
    }
}
