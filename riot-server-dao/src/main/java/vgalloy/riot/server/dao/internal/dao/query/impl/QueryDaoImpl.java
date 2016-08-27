package vgalloy.riot.server.dao.internal.dao.query.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.api.entity.Position;
import vgalloy.riot.server.dao.internal.dao.factory.MongoClientFactory;
import vgalloy.riot.server.dao.internal.dao.query.mapper.PositionMapper;
import vgalloy.riot.server.dao.internal.timertask.factory.TaskFactory;
import vgalloy.riot.server.dao.internal.timertask.impl.UpdatePositionTask;

import static java.util.Arrays.asList;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 14/06/16.
 */
public final class QueryDaoImpl implements QueryDao {

    private final MongoDatabase mongoDatabase;

    /**
     * Constructor.
     *
     * @param databaseUrl  the database url
     * @param databaseName the database name
     */
    public QueryDaoImpl(String databaseUrl, String databaseName) {
        MongoClient mongoClient = MongoClientFactory.get(databaseUrl);
        mongoDatabase = mongoClient.getDatabase(databaseName);
        TaskFactory.startTask(new UpdatePositionTask(mongoDatabase), 15 * 60 * 1000);
    }

    @Override
    public Map<Integer, Double> getWinRate(int championId) {
        Map<Integer, Double> map = new HashMap<>();
        FindIterable<Document> result = mongoDatabase.getCollection("winRate").find(new Document("_id.championId", championId));
        for (Document o : result) {
            Integer index = ((Document) o.get("_id")).getInteger("played");
            map.put(index, Math.floor(1000 * o.getDouble("result")) / 10);
        }
        return map;
    }

    @Override
    public List<List<Position>> getPosition(long summonerId, int championId) {
        AggregateIterable<Document> result = mongoDatabase.getCollection("positions").aggregate(asList(
                new BasicDBObject("$match", new BasicDBObject("value.players.summonerId", summonerId)),
                new BasicDBObject("$match", new BasicDBObject("value.players.championId", championId)),
                new BasicDBObject("$unwind", "$value.players"),
                new BasicDBObject("$match", new BasicDBObject("value.players.summonerId", summonerId)),
                new BasicDBObject("$match", new BasicDBObject("value.players.championId", championId))
        ));

        List<List<Position>> positionResultList = new ArrayList<>();
        for (Document document : result) {
            Document game = (Document) document.get("value");
            Document players = (Document) game.get("players");
            List<Document> positionsAsDocument = (List<Document>) players.get("positions");
            List<Position> positions = positionsAsDocument.stream().map(PositionMapper::map).collect(Collectors.toList());
            positionResultList.add(positions);
        }

        return positionResultList;
    }
}
