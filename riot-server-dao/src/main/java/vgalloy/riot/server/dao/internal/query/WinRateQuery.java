package vgalloy.riot.server.dao.internal.query;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import vgalloy.riot.server.dao.internal.dao.commondao.impl.RankedStatsDaoImpl;
import vgalloy.riot.server.dao.internal.dao.factory.MongoDatabaseFactory;

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
     * Get the winRate of a champion as a map where the key is the number of game played.
     *
     * @param mongoDatabaseFactory the mongo database factory
     * @param championId           the champion id
     * @return the winRate
     */
    public static Map<Integer, Double> getWinRate(MongoDatabaseFactory mongoDatabaseFactory, int championId) {
        Map<Integer, Double> map = new HashMap<>();
        FindIterable<Document> result = mongoDatabaseFactory.getMongoCollection("winRate").get().find(new Document("_id.championId", championId));
        for (Document o : result) {
            Integer index = ((Document) o.get("_id")).getInteger("played");
            map.put(index, Math.floor(1000 * o.getDouble("result")) / 10);
        }
        return map;
    }

    /**
     * Update the winRate table.
     *
     * @param mongoDatabaseFactory the mongo database factory
     */
    public static void updateWinRate(MongoDatabaseFactory mongoDatabaseFactory) {
        mongoDatabaseFactory.getMongoCollection(RankedStatsDaoImpl.COLLECTION_NAME).get().aggregate(Arrays.asList(
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
