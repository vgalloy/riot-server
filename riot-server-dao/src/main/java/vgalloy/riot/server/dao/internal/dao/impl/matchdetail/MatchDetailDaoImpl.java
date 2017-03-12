package vgalloy.riot.server.dao.internal.dao.impl.matchdetail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.dpoid.DpoId;
import vgalloy.riot.server.dao.api.entity.dpoid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.dao.api.mapper.MatchDetailIdMapper;
import vgalloy.riot.server.dao.internal.dao.factory.MatchDetailHelper;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import vgalloy.riot.server.dao.internal.dao.impl.GenericDaoImpl;
import vgalloy.riot.server.dao.internal.entity.dpo.AbstractDpo;
import vgalloy.riot.server.dao.internal.entity.dpo.MatchDetailDpo;
import vgalloy.riot.server.dao.internal.entity.mapper.DpoIdMapper;
import vgalloy.riot.server.dao.internal.entity.mapper.MatchDetailMapper;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class MatchDetailDaoImpl implements MatchDetailDao {

    public static final String COLLECTION_NAME = "matchDetail";
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDetailDaoImpl.class);

    private final String databaseUrl;
    private final String databaseName;

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public MatchDetailDaoImpl(String databaseUrl, String databaseName) {
        this.databaseUrl = Objects.requireNonNull(databaseUrl);
        this.databaseName = Objects.requireNonNull(databaseName);
    }

    @Override
    public void save(MatchDetailWrapper matchDetailWrapper) {
        Objects.requireNonNull(matchDetailWrapper);

        JacksonDBCollection<MatchDetailDpo, String> collection = getCollection(matchDetailWrapper.getItemId().getMatchDate());
        MatchDetailDpo dataObject = new MatchDetailDpo(matchDetailWrapper.getItemId().getRegion(), matchDetailWrapper.getItemId().getId(), matchDetailWrapper.getItemId().getMatchDate().toEpochDay());
        matchDetailWrapper.getItem().ifPresent(dataObject::setItem);
        new GenericDaoImpl<>(collection).update(dataObject);
    }

    @Override
    public Optional<Entity<MatchDetail, MatchDetailId>> get(MatchDetailId matchDetailId) {
        Objects.requireNonNull(matchDetailId);

        JacksonDBCollection<MatchDetailDpo, String> collection = getCollection(matchDetailId.getMatchDate());
        return new GenericDaoImpl<>(collection).getById(DpoIdMapper.toNormalizeString(matchDetailId))
                .map(MatchDetailMapper::mapToEntity)
                .map(Optional::of)
                .orElse(Optional.empty());
    }

    @Override
    public List<MatchDetail> findMatchDetailBySummonerId(DpoId summonerId, LocalDateTime from, LocalDateTime to) {
        Objects.requireNonNull(summonerId);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        LocalDateTime currentDate = from;
        List<MatchDetail> result = new ArrayList<>();

        while (currentDate.toLocalDate().isBefore(to.plus(1, ChronoUnit.DAYS).toLocalDate())) {
            JacksonDBCollection<MatchDetailDpo, String> collection = getCollection(currentDate.toLocalDate());
            result.addAll(collection.find(DBQuery.is("item.participantIdentities.player.summonerId", summonerId.getId()))
                    .and(DBQuery.is("region", summonerId.getRegion()))
                    .and(DBQuery.greaterThanEquals("item.matchCreation", from.toEpochSecond(ZoneOffset.UTC) * 1000))
                    .and(DBQuery.lessThan("item.matchCreation", to.toEpochSecond(ZoneOffset.UTC) * 1000))
                    .sort(new BasicDBObject("item.matchCreation", 1))
                    .toArray()
                    .stream()
                    .map(AbstractDpo::getItem)
                    .collect(Collectors.toList()));

            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        return result;
    }

    @Override
    public List<MatchDetailId> cleanAllMatchForADay(LocalDate localDate) {
        Objects.requireNonNull(localDate);

        JacksonDBCollection<MatchDetailDpo, String> collection = getCollection(localDate);
        List<MatchDetailId> result = collection.find()
                .toArray()
                .stream()
                .map(AbstractDpo::getItem)
                .map(MatchDetailIdMapper::map)
                .collect(Collectors.toList());
        collection.remove(DBQuery.empty());
        collection.drop();
        return result;
    }

    @Override
    public void remove(MatchDetailId matchDetailId) {
        Objects.requireNonNull(matchDetailId);

        getCollection(matchDetailId.getMatchDate()).removeById(DpoIdMapper.toNormalizeString(matchDetailId));
    }

    /**
     * Create or update index.
     *
     * @param dbCollection the db collection
     */
    private static void createOrUpdateIndexes(DBCollection dbCollection) {
        LOGGER.debug("start index creation");
        dbCollection.createIndex(new BasicDBObject("region", 1));
        dbCollection.createIndex(new BasicDBObject("item.participantIdentities.player.summonerId", 1));
        LOGGER.debug("end index creation");
    }

    /**
     * Get the corresponding mongo jack collection.
     *
     * @param localDate the index date
     * @return the mongoJackCollection
     */
    private JacksonDBCollection<MatchDetailDpo, String> getCollection(LocalDate localDate) {
        DBCollection dbCollection = MongoDriverObjectFactory.getMongoClient(databaseUrl)
                .getDB(databaseName)
                .getDBCollection(MatchDetailHelper.getCollectionName(localDate))
                .get();

        createOrUpdateIndexes(dbCollection);
        return JacksonDBCollection.wrap(dbCollection, MatchDetailDpo.class, String.class);
    }
}
