package vgalloy.riot.server.dao.internal.task.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.api.dao.MatchDetailDao;
import vgalloy.riot.server.dao.internal.task.Task;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public final class DatabaseCleanerTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleanerTask.class);

    private final MatchDetailDao matchDetailDao;

    /**
     * Constructor.
     *
     * @param matchDetailDao the match detail dao
     */
    public DatabaseCleanerTask(MatchDetailDao matchDetailDao) {
        this.matchDetailDao = Objects.requireNonNull(matchDetailDao);
    }

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        LocalDate cleanDay = LocalDate.now().minus(2, ChronoUnit.MONTHS);
        LOGGER.info("[ START ] : clean database for day : {}", cleanDay);
        matchDetailDao.cleanAllMatchForADay(cleanDay);
        LOGGER.info("[ END   ] : clean database {} ms", System.currentTimeMillis() - startTime);
    }
}
