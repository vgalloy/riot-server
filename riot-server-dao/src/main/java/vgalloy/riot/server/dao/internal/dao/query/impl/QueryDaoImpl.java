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

import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.RankedStatsDaoImpl;
import vgalloy.riot.server.dao.internal.dao.factory.MongoClientFactory;
import vgalloy.riot.server.dao.api.dao.QueryDao;
import vgalloy.riot.server.dao.internal.dao.query.mapper.PositionMapper;
import vgalloy.riot.server.dao.api.entity.Position;

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
    public void updateWinRate() {
        mongoDatabase.getCollection(RankedStatsDaoImpl.COLLECTION_NAME).aggregate(asList(
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

    @Override
    public void updatePosition() {
        String mapFunction = "function() {" +
                "key = this._id;" +
                "result = {};" +
                "result.players = [];" +
                "if(this.item.participantIdentities != undefined) {" +
                "this.item.participantIdentities.forEach(function(participantIdentity) {" +
                "identity = {};" +
                "identity.summonerId = participantIdentity.player.summonerId;" +
                "identity.participantId = participantIdentity.participantId;" +
                "result.players[participantIdentity.participantId] = identity;" +
                "});" +
                "" +
                "if(this.item.participants != undefined) {" +
                "this.item.participants.forEach(function(participant) {" +
                "result.players[participant.participantId].championId = participant.championId;" +
                "});" +
                "" +
                "result.players.forEach(function(player) {" +
                "player.positions = [];" +
                "});" +
                "if(this.item.timeline != undefined && this.item.timeline.frames != undefined) {" +
                "this.item.timeline.frames.forEach(function(frame) {" +
                "result.players.forEach(function(player) {" +
                "position = frame.participantFrames[player.participantId].position;" +
                "if(position != null) {" +
                "player.positions.push(position);" +
                "}" +
                "});" +
                "});" +
                "emit(key, result);" +
                "}" +
                "}" +
                "}" +
                "};";
        String reduceFunction = "function(key, values) {" +
                "return result;" +
                "};";
        mongoDatabase.getCollection(MatchDetailDaoImpl.COLLECTION_NAME).mapReduce(mapFunction, reduceFunction)
                .collectionName("positions").first();
    }
}
