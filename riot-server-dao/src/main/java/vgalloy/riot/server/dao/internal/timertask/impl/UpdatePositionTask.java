package vgalloy.riot.server.dao.internal.timertask.impl;

import java.util.Objects;

import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.internal.dao.commondao.impl.MatchDetailDaoImpl;
import vgalloy.riot.server.dao.internal.timertask.Task;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public class UpdatePositionTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePositionTask.class);

    private final MongoDatabase mongoDatabase;

    /**
     * Constructor.
     *
     * @param mongoDatabase the mongo database
     */
    public UpdatePositionTask(MongoDatabase mongoDatabase) {
        this.mongoDatabase = Objects.requireNonNull(mongoDatabase);
    }

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ START ] : updatePosition");
        updatePosition();
        LOGGER.info("[ END ] : updatePosition {} ms", System.currentTimeMillis() - startTime);
    }

    // @formatter:off
    /**
     * Based on map reduce operation, this method only keep create a collection of players with the position during the
     * game.
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
    private void updatePosition() {
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
