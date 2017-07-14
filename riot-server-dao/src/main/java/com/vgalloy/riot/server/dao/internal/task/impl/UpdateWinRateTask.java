package com.vgalloy.riot.server.dao.internal.task.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vgalloy.riot.server.dao.internal.dao.factory.MongoDatabaseFactory;
import com.vgalloy.riot.server.dao.internal.dao.impl.champion.WinRateHelper;
import com.vgalloy.riot.server.dao.internal.task.Task;

/**
 * Created by Vincent Galloy on 27/08/16.
 *
 * @author Vincent Galloy
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
