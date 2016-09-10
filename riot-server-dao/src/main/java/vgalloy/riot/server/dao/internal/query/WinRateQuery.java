package vgalloy.riot.server.dao.internal.query;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import vgalloy.riot.server.dao.api.entity.WinRate;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RankedStatsDaoImpl;

import static java.util.Arrays.asList;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 10/09/16.
 */
public final class WinRateQuery {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private WinRateQuery() {
        throw new AssertionError();
    }

    /**
     * Get the champion win rate during the given period.
     * // TODO On fait une requête par jour. Il faudrait en avoir une en tout.
     *
     * @param mongoDatabase the mongo database
     * @param championId    the champion id
     * @param startDate     the start date (included)
     * @param endDate       the end date (excluded)
     * @return the win rate of the champion. Each entry is given with a timestamp.
     */
    public static Map<LocalDate, WinRate> getWinRate(MongoDatabase mongoDatabase, int championId, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, WinRate> result = new HashMap<>();
        for (long i = startDate.toEpochDay(); i < endDate.toEpochDay(); i++) {
            LocalDate localDate = LocalDate.ofEpochDay(i);
            result.put(localDate, WinRateQuery.getWinRate(mongoDatabase, championId, localDate));
        }
        return result;
    }

    /**
     * Get the champion win rate during the given day.
     *
     * @param mongoDatabase the mongo database
     * @param championId    the champion id
     * @param startDate     the day to analyse
     * @return the win rate of the champion during the day
     */
    public static WinRate getWinRate(MongoDatabase mongoDatabase, int championId, LocalDate startDate) {

        BasicDBObject projectObject = new BasicDBObject("item.matchDuration", 1);
        projectObject.put("item.participants.championId", 1);
        projectObject.put("item.participants.stats.winner", 1);

        BasicDBObject groupObject = new BasicDBObject("_id", "$item.participants.stats.winner");
        groupObject.put("value", new BasicDBObject("$sum", 1));

        AggregateIterable<Document> result = mongoDatabase.getCollection(MatchDetailDaoImpl.COLLECTION_NAME).aggregate(asList(
                new BasicDBObject("$match", new BasicDBObject("item.participants.championId", championId)),
                new BasicDBObject("$match", new BasicDBObject("item.matchCreation", new BasicDBObject("$gt", startDate.toEpochDay() * 3600 * 24))),
                new BasicDBObject("$match", new BasicDBObject("item.matchCreation", new BasicDBObject("$lt", (startDate.toEpochDay() + 1) * 3600 * 24))),
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
        Objects.requireNonNull(win);
        Objects.requireNonNull(lose);
        return new WinRate(win, lose);
    }

    /**
     * Get the winRate of a champion as a map where the key is the number of game played.
     *
     * @param mongoDatabase the mongo database
     * @param championId    the champion id
     * @return the winRate
     */
    public static Map<Integer, Double> getWinRate(MongoDatabase mongoDatabase, int championId) {
        Map<Integer, Double> map = new HashMap<>();
        FindIterable<Document> result = mongoDatabase.getCollection("winRate").find(new Document("_id.championId", championId));
        for (Document o : result) {
            Integer index = ((Document) o.get("_id")).getInteger("played");
            map.put(index, Math.floor(1000 * o.getDouble("result")) / 10);
        }
        return map;
    }

    /**
     * Update the winRate table.
     *
     * @param mongoDatabase the mongo database
     */
    public static void updateWinRate(MongoDatabase mongoDatabase) {
        mongoDatabase.getCollection(RankedStatsDaoImpl.COLLECTION_NAME).aggregate(Arrays.asList(
                new BasicDBObject("$unwind", "$item.champions"),
                new BasicDBObject("$group",
                        new Document("_id", new Document("championId", "$item.champions.id").append("played", "$item.champions.stats.totalSessionsPlayed"))
                                .append("played", new Document("$sum", "$item.champions.stats.totalSessionsPlayed"))
                                .append("won", new Document("$sum", "$item.champions.stats.totalSessionsWon"))
                                .append("total", new Document("$sum", 1))
                ),
                new BasicDBObject("$project", new Document("result", new Document("$divide", new String[]{"$won", "$played"})).append("total", 1)),
                new BasicDBObject("$sort", new Document("_id", 1)),
                new BasicDBObject("$out", "winRate")
        )).iterator();
    }
}
