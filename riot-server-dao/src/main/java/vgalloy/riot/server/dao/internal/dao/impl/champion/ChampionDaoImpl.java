package vgalloy.riot.server.dao.internal.dao.impl.champion;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.mongojack.DBQuery;

import vgalloy.riot.api.api.constant.Region;
import vgalloy.riot.api.api.dto.lolstaticdata.ChampionDto;
import vgalloy.riot.server.dao.api.dao.ChampionDao;
import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.internal.dao.AbstractDao;
import vgalloy.riot.server.dao.internal.dao.factory.MatchDetailHelper;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDatabaseFactory;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDriverObjectFactory;
import vgalloy.riot.server.dao.internal.entity.dpo.ChampionDpo;

/**
 * Created by Vincent Galloy on 28/05/16.
 *
 * @author Vincent Galloy
 */
public final class ChampionDaoImpl extends AbstractDao<ChampionDto, ChampionDpo> implements ChampionDao {

    public static final String COLLECTION_NAME = "champion";

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public ChampionDaoImpl(String databaseUrl, String databaseName) {
        super(databaseUrl, databaseName, COLLECTION_NAME);
    }

    @Override
    public Map<Integer, Double> getWinRateByGamePlayed(int championId) {
        MongoDatabaseFactory mongoDatabaseFactory = MongoDriverObjectFactory.getMongoClient(getDatabaseUrl()).getMongoDatabase(getDatabaseName());
        return WinRateHelper.getWinRate(mongoDatabaseFactory, championId);
    }

    @Override
    public Map<LocalDate, WinRate> getWinRateDuringPeriodOfTime(int championId, LocalDate from, LocalDate to) {
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
        MongoCollection<Document> collection = MongoDriverObjectFactory.getMongoClient(getDatabaseUrl())
            .getMongoDatabase(getDatabaseName())
            .getMongoCollection(MatchDetailHelper.getCollectionName(localDate))
            .get();

        BasicDBObject projectObject = new BasicDBObject("item.participants.championId", 1);
        projectObject.put("item.participants.stats.winner", 1);

        BasicDBObject groupObject = new BasicDBObject("_id", "$item.participants.stats.winner");
        groupObject.put("value", new BasicDBObject("$sum", 1));

        AggregateIterable<Document> aggregationResult = collection.aggregate(Arrays.asList(
            new BasicDBObject("$match", new BasicDBObject("item.participants.championId", championId)),
            new BasicDBObject("$project", projectObject),
            new BasicDBObject("$unwind", "$item.participants"),
            new BasicDBObject("$match", new BasicDBObject("item.participants.championId", championId)),
            new BasicDBObject("$group", groupObject)
        ));

        Integer win = 0;
        Integer lose = 0;
        for (Document o : aggregationResult) {
            boolean index = o.getBoolean("_id");
            if (index) {
                win = o.getInteger("value");
            } else {
                lose = o.getInteger("value");
            }
        }

        return new WinRate(win, lose);
    }

    @Override
    public Map<Integer, WinRate> getWinRateForAllChampion(LocalDate date) {
        MongoCollection<Document> collection = MongoDriverObjectFactory.getMongoClient(getDatabaseUrl())
            .getMongoDatabase(getDatabaseName())
            .getMongoCollection(MatchDetailHelper.getCollectionName(date))
            .get();

        BasicDBObject projectObject = new BasicDBObject("item.participants.championId", 1);
        projectObject.put("item.participants.stats.winner", 1);

        BasicDBObject test = new BasicDBObject("champion", "$item.participants.championId");
        test.put("winner", "$item.participants.stats.winner");

        BasicDBObject groupObject = new BasicDBObject("_id", test);
        groupObject.put("value", new BasicDBObject("$sum", 1));

        AggregateIterable<Document> aggregationResult = collection.aggregate(Arrays.asList(
            new BasicDBObject("$project", projectObject),
            new BasicDBObject("$unwind", "$item.participants"),
            new BasicDBObject("$group", groupObject)
        ));

        Map<Integer, WinRate> result = new HashMap<>();

        for (Document o : aggregationResult) {
            Integer championId = Integer.class.cast(Map.class.cast(o.get("_id")).get("champion"));
            Boolean win = Boolean.class.cast(Map.class.cast(o.get("_id")).get("winner"));
            Integer value = o.getInteger("value");

            WinRate currentWinRate = result.getOrDefault(championId, new WinRate(0, 0));

            if (win) {
                result.put(championId, new WinRate(value, currentWinRate.getLose()));
            } else {
                result.put(championId, new WinRate(currentWinRate.getWin(), value));
            }
        }
        return result;
    }

    @Override
    public List<ChampionDto> findChampionByName(Region region, String championName) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(championName);

        DBQuery.Query dbQuery = DBQuery.in("region", region)
            .and(DBQuery.regex("item.name", Pattern.compile(championName + "*")));

        return collection.find(dbQuery)
            .limit(5)
            .toArray()
            .stream()
            .map(ChampionDpo::getItem)
            .collect(Collectors.toList());
    }
}
