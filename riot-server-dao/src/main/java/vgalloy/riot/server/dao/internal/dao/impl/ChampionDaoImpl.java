package vgalloy.riot.server.dao.internal.dao.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.internal.dao.AbstractDao;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDatabaseFactory;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import vgalloy.riot.server.dao.internal.dao.impl.matchdetail.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.entity.dpo.ChampionDpo;
import vgalloy.riot.server.dao.internal.exception.MongoDaoException;
import vgalloy.riot.server.dao.internal.query.WinRateQuery;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 28/05/16.
 */
public final class ChampionDaoImpl extends AbstractDao<ChampionDto, ChampionDpo> implements ChampionDao {

    public static final String COLLECTION_NAME = "champion";

    private final String databaseUrl;
    private final String databaseName;

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public ChampionDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
        this.databaseUrl = Objects.requireNonNull(databaseUrl);
        this.databaseName = Objects.requireNonNull(databaseName);
    }

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        MongoDatabaseFactory mongoDatabaseFactory = MongoDriverObjectFactory.getMongoClient(databaseUrl).getMongoDatabase(databaseName);
        return WinRateQuery.getWinRate(mongoDatabaseFactory, championId);
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

    @Override
    public WinRate getWinRate(int championId, LocalDate localDate) {
        BasicDBObject projectObject = new BasicDBObject("item.participants.championId", 1);
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
        return MatchDetailDaoImpl.COLLECTION_NAME + "_" + localDate.format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
    }
}
