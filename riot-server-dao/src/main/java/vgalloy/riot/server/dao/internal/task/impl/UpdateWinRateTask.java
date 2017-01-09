package vgalloy.riot.server.dao.internal.task.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.internal.dao.factory.MongoDatabaseFactory;
import vgalloy.riot.server.dao.internal.dao.impl.champion.WinRateHelper;
import vgalloy.riot.server.dao.internal.task.Task;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public final class UpdateWinRateTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateWinRateTask.class);

    private final MongoDatabaseFactory mongoDatabaseFactory;

    /**
     * Constructor.
     *
     * @param mongoDatabaseFactory the mongo database factory
     */
    public UpdateWinRateTask(MongoDatabaseFactory mongoDatabaseFactory) {
        this.mongoDatabaseFactory = Objects.requireNonNull(mongoDatabaseFactory);
    }

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ START ] : updateWinRate");
        WinRateHelper.updateWinRate(mongoDatabaseFactory);
        LOGGER.info("[ END   ] : updateWinRate {} ms", System.currentTimeMillis() - startTime);
    }
}
