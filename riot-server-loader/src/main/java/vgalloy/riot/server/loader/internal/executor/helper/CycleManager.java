package vgalloy.riot.server.loader.internal.executor.helper;

import vgalloy.riot.server.loader.api.service.exception.LoaderException;

/**
 * Created by Vincent Galloy on 09/12/16.
 *
 * @author Vincent Galloy
 */
public final class CycleManager {

    private static final long DEFAULT_SLEEPING_TIME_MILLIS = 1_000;
    private static final long ONE_MINUTE = 60 * 1000;

    private final long sleeperCreationMillis = System.currentTimeMillis();
    private long sleepingTimeMillis;
    private int iteration = 1;

    /**
     * Constructor.
     */
    public CycleManager() {
        sleepingTimeMillis = DEFAULT_SLEEPING_TIME_MILLIS;
    }

    /**
     * Sleep.
     */
    public void sleep() {
        try {
            Thread.sleep(sleepingTimeMillis);
        } catch (InterruptedException e1) {
            throw new LoaderException(e1);
        }
    }

    /**
     * Decide if the request should be try again. And increment iteration.
     *
     * @return false if the request should not be try again
     */
    public boolean tryAgain() {
        iteration++;
        sleepingTimeMillis = Math.min(ONE_MINUTE, 2 * sleepingTimeMillis);
        return iteration <= 60;
    }

    /**
     * Get the total sleeper life time.
     *
     * @return the life time in millis
     */
    public long totalExecutionTimeMillis() {
        return System.currentTimeMillis() - sleeperCreationMillis;
    }

    public long getSleepingTimeMillis() {
        return sleepingTimeMillis;
    }

    public int getIteration() {
        return iteration;
    }
}
