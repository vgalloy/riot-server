package vgalloy.riot.server.dao.internal.task.impl;

import java.util.Objects;

import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.internal.query.WinRateQuery;
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
        WinRateQuery.updateWinRate(mongoDatabase);
        LOGGER.info("[ END   ] : updateWinRate {} ms", System.currentTimeMillis() - startTime);
    }
}
