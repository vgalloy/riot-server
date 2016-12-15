package vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.matchdetail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mongodb.DBCollection;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.dao.internal.mongo.dao.commondao.impl.GenericMongoDaoImpl;
import vgalloy.riot.server.dao.internal.mongo.entity.dataobject.AbstractDataPersitenceObject;
import vgalloy.riot.server.dao.internal.mongo.entity.dataobject.MatchDetailDpo;
import vgalloy.riot.server.dao.internal.mongo.entity.mapper.DpoIdMapper;
import vgalloy.riot.server.dao.internal.mongo.entity.mapper.MatchDetailMapper;
import vgalloy.riot.server.dao.internal.mongo.exception.MongoDaoException;
import vgalloy.riot.server.dao.internal.mongo.factory.MongoDriverObjectFactory;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class MatchDetailMongoDaoImpl implements MatchDetailDao {

    public static final String COLLECTION_NAME = "matchDetail";

    private final String databaseUrl;
    private final String databaseName;

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public MatchDetailMongoDaoImpl(String databaseUrl, String databaseName) {
        this.databaseUrl = Objects.requireNonNull(databaseUrl);
        this.databaseName = Objects.requireNonNull(databaseName);
    }

    /**
     * Create the collection name base on the date.
     *
     * @param localDate the local
     * @return the collection name
     */
    private static String getCollectionName(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        if (localDate.isBefore(LocalDate.now().minus(4, ChronoUnit.YEARS))) {
            throw new MongoDaoException("the date " + localDate + " is to old");
        }
        if (localDate.isAfter(LocalDate.now().plus(1, ChronoUnit.DAYS))) {
            throw new MongoDaoException("the date " + localDate + " is in the future");
        }
        return COLLECTION_NAME + "_" + localDate.format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
    }

    @Override
    public void save(MatchDetailWrapper matchDetailWrapper) {
        Objects.requireNonNull(matchDetailWrapper);

        JacksonDBCollection<MatchDetailDpo, String> collection = getCollection(matchDetailWrapper.getItemId().getMatchDate());
        MatchDetailDpo dataObject = new MatchDetailDpo(matchDetailWrapper.getItemId().getRegion(), matchDetailWrapper.getItemId().getId(), matchDetailWrapper.getItemId().getMatchDate().toEpochDay());
        matchDetailWrapper.getItem().ifPresent(dataObject::setItem);
        new GenericMongoDaoImpl<>(collection).update(dataObject);
    }

    @Override
    public Optional<Entity<MatchDetail, MatchDetailId>> get(MatchDetailId matchDetailId) {
        Objects.requireNonNull(matchDetailId);

        JacksonDBCollection<MatchDetailDpo, String> collection = getCollection(matchDetailId.getMatchDate());
        Optional<MatchDetailDpo> dataObject = new GenericMongoDaoImpl<>(collection).getById(DpoIdMapper.toNormalizeString(matchDetailId));
        if (dataObject.isPresent()) {
            return Optional.of(MatchDetailMapper.mapToEntity(dataObject.get()));
        }

        return Optional.empty();
    }

    @Override
    public List<MatchDetail> findMatchDetailBySummonerId(Region region, long summonerId, LocalDate from, LocalDate to) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        LocalDate currentDate = from;
        List<MatchDetail> result = new ArrayList<>();

        while (currentDate.isBefore(to)) {
            JacksonDBCollection<MatchDetailDpo, String> collection = getCollection(currentDate);
            result.addAll(collection.find(DBQuery.is("item.participantIdentities.player.summonerId", summonerId))
                    .and(DBQuery.is("region", region))
                    .toArray()
                    .stream()
                    .map(AbstractDataPersitenceObject::getItem)
                    .collect(Collectors.toList()));

            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        return result;
    }

    /**
     * Get the corresponding mongo jack collection.
     *
     * @param localDate the index date
     * @return the mongoJackCollection
     */
    private JacksonDBCollection<MatchDetailDpo, String> getCollection(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        DBCollection dbCollection = MongoDriverObjectFactory.getMongoClient(databaseUrl)
                .getDB(databaseName)
                .getDBCollection(getCollectionName(localDate))
                .get();

        return JacksonDBCollection.wrap(dbCollection, MatchDetailDpo.class, String.class);
    }
}
