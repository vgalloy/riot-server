package vgalloy.riot.server.dao.internal.task.impl;

import java.util.Objects;

import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.internal.query.PositionQuery;
import vgalloy.riot.server.dao.internal.task.Task;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public final class UpdatePositionTask implements Task {

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
        PositionQuery.updatePosition(mongoDatabase);
        LOGGER.info("[ END   ] : updatePosition {} ms", System.currentTimeMillis() - startTime);
    }
}
