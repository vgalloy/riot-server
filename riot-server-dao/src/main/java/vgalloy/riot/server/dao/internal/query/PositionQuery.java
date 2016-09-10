package vgalloy.riot.server.dao.internal.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import vgalloy.riot.server.dao.api.entity.Position;
import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.entity.mapper.PositionMapper;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 10/09/16.
 */
public final class PositionQuery {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private PositionQuery() {
        throw new AssertionError();
    }

    /**
     * Get the position of a summoner during all game played with the given champion.
     *
     * @param mongoDatabase the mongo database
     * @param summonerId the summoner id
     * @param championId the champion id
     * @return a list with all the game position. Each game is defined as a list of position
     */
    public static List<List<Position>> getPosition(MongoDatabase mongoDatabase, long summonerId, int championId) {
        AggregateIterable<Document> result = mongoDatabase.getCollection("positions").aggregate(Arrays.asList(
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

    // @formatter:off
    /**
     * Based on map reduce operation, this method only keep create a collection of players with the position during the
     * game.
     * @param mongoDatabase the mongo database
     * {
     *     _id : {},
     *     value : {
     *         players : [
     *              {
     *                  summonerId : {},
     *                  championId : {},
     *                  positions : [
     *                      {
     *                          x : {},
     *                          y : {}
     *                      }
     *                  ]
     *              }
     *         ]
     *     }
     * }
     */
    // @formatter:on
    public static void updatePosition(MongoDatabase mongoDatabase) {
        // @formatter:off
        String mapFunction =
                "function() {" +
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
                        "if(this.item.participants != undefined) {" +
                            "this.item.participants.forEach(function(participant) {" +
                                "result.players[participant.participantId].championId = participant.championId;" +
                            "});" +
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
    // @formatter:on
        String reduceFunction = "function(key, values) {" +
                "return result;" +
                "};";
        mongoDatabase.getCollection(MatchDetailDaoImpl.COLLECTION_NAME)
                .mapReduce(mapFunction, reduceFunction)
                .collectionName("positions")
                .first();
    }
}
