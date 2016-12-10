package vgalloy.riot.server.loader.internal.executor.helper;

/**
 * @author Vincent Galloy - 09/12/16
 *         Created by Vincent Galloy on 09/12/16.
 */
public final class Sleeper {

    private static final long DEFAULT_SLEEPING_TIME_MILLIS = 2_000;
    private static final long ONE_MINUTE = 60 * 1000;

    private final long sleeperCreationMillis = System.currentTimeMillis();
    private long sleepingTimeMillis;

    /**
     * Constructor.
     */
    public Sleeper() {
        sleepingTimeMillis = DEFAULT_SLEEPING_TIME_MILLIS;
    }

    /**
     * Sleep and increment the internal time.
     */
    public void sleepAndIncrementTimer() {
        sleepSilently();
        sleepingTimeMillis = Math.min(60 * ONE_MINUTE, 2 * sleepingTimeMillis);
    }

    /**
     * Get the total sleeper life time.
     *
     * @return the life time in millis
     */
    public long totalExecutionTimeMillis() {
        return System.currentTimeMillis() - sleeperCreationMillis;
    }

    /**
     * Slow down the request execution.
     */
    private void sleepSilently() {
        try {
            Thread.sleep(sleepingTimeMillis);
        } catch (InterruptedException e1) {
            throw new RuntimeException(e1);
        }
    }

    public long getSleepingTimeMillis() {
        return sleepingTimeMillis;
    }
}
