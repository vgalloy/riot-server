package vgalloy.riot.server.dao.internal.task.impl;

import java.util.Arrays;
import java.util.Objects;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.internal.dao.commondao.impl.RankedStatsDaoImpl;
import vgalloy.riot.server.dao.internal.task.Task;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public final class UpdateWinRateTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateWinRateTask.class);

    private final MongoDatabase mongoDatabase;

    /**
     * Constructor.
     *
     * @param mongoDatabase the mongo database
     */
    public UpdateWinRateTask(MongoDatabase mongoDatabase) {
        this.mongoDatabase = Objects.requireNonNull(mongoDatabase);
    }

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ START ] : updateWinRate");
        updateWinRate();
        LOGGER.info("[ END   ] : updateWinRate {} ms", System.currentTimeMillis() - startTime);
    }

    /**
     * Update the winRate table.
     */
    private void updateWinRate() {
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
