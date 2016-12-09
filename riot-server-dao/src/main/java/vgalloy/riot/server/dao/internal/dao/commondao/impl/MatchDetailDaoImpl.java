package vgalloy.riot.server.dao.internal.dao.commondao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.mach.MatchDetail;
import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.api.entity.Entity;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.api.entity.itemid.MatchDetailId;
import vgalloy.riot.server.dao.api.entity.wrapper.MatchDetailWrapper;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import vgalloy.riot.server.dao.internal.entity.dataobject.AbstractDataObject;
import vgalloy.riot.server.dao.internal.entity.dataobject.MatchDetailDo;
import vgalloy.riot.server.dao.internal.entity.mapper.ItemIdMapper;
import vgalloy.riot.server.dao.internal.entity.mapper.MatchDetailMapper;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class MatchDetailDaoImpl implements MatchDetailDao {

    public static final String COLLECTION_NAME = "matchDetail";

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

        JacksonDBCollection<MatchDetailDo, String> collection = getCollection(matchDetailWrapper.getItemId().getMatchDate());
        MatchDetailDo dataObject = new MatchDetailDo(matchDetailWrapper.getItemId().getRegion(), matchDetailWrapper.getItemId().getId(), matchDetailWrapper.getItemId().getMatchDate().toEpochDay());
        matchDetailWrapper.getItem().ifPresent(dataObject::setItem);
        new GenericDaoImpl<>(collection).update(dataObject);
    }

    @Override
    public Optional<Entity<MatchDetail, MatchDetailId>> get(MatchDetailId matchDetailId) {
        Objects.requireNonNull(matchDetailId);

        JacksonDBCollection<MatchDetailDo, String> collection = getCollection(matchDetailId.getMatchDate());
        Optional<MatchDetailDo> dataObject = new GenericDaoImpl<>(collection).getById(ItemIdMapper.toNormalizeString(matchDetailId));
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
            JacksonDBCollection<MatchDetailDo, String> collection = getCollection(currentDate);
            result.addAll(collection.find(DBQuery.is("item.participantIdentities.player.summonerId", summonerId))
                    .and(DBQuery.is("region", region))
                    .toArray()
                    .stream()
                    .map(AbstractDataObject::getItem)
                    .collect(Collectors.toList()));

            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        return result;
    }

    @Override
    public Map<LocalDate, WinRate> getWinRate(int championId, LocalDate from, LocalDate to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        LocalDate currentDate = from;
        Map<LocalDate, WinRate> result = new HashMap<>();

        while (currentDate.isBefore(to)) {
            result.put(currentDate, getWinRate(championId, currentDate));
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        return result;
    }

    /**
     * Get the champion win rate during the given day.
     *
     * @param championId the champion id
     * @param localDate  the day to analyse
     * @return the win rate of the champion during the day
     */
    @Override
    public WinRate getWinRate(int championId, LocalDate localDate) {
        BasicDBObject projectObject = new BasicDBObject("item.matchDuration", 1);
        projectObject.put("item.participants.championId", 1);
        projectObject.put("item.participants.stats.winner", 1);

        BasicDBObject groupObject = new BasicDBObject("_id", "$item.participants.stats.winner");
        groupObject.put("value", new BasicDBObject("$sum", 1));

        MongoCollection<Document> collection = MongoDriverObjectFactory.getMongoClient(databaseUrl)
                .getMongoDatabase(databaseName)
                .getMongoCollection(getCollectionName(localDate))
                .get();

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new BasicDBObject("$match", new BasicDBObject("item.participants.championId", championId)),
                new BasicDBObject("$project", projectObject),
                new BasicDBObject("$unwind", "$item.participants"),
                new BasicDBObject("$match", new BasicDBObject("item.participants.championId", championId)),
                new BasicDBObject("$group", groupObject)
        ));

        Integer win = 0;
        Integer lose = 0;
        for (Document o : result) {
            boolean index = o.getBoolean("_id");
            if (index) {
                win = o.getInteger("value");
            } else {
                lose = o.getInteger("value");
            }
        }

        return new WinRate(win, lose);
    }

    /**
     * Get the corresponding mongo jack collection.
     *
     * @param localDate the index date
     * @return the mongoJackCollection
     */
    private JacksonDBCollection<MatchDetailDo, String> getCollection(LocalDate localDate) {
        Objects.requireNonNull(localDate);
        DBCollection dbCollection = MongoDriverObjectFactory.getMongoClient(databaseUrl)
                .getDB(databaseName)
                .getDBCollection(getCollectionName(localDate))
                .get();

        return JacksonDBCollection.wrap(dbCollection, MatchDetailDo.class, String.class);
    }
}
