package vgalloy.riot.server.loader.internal.executor.helper;

/**
 * @author Vincent Galloy - 09/12/16
 *         Created by Vincent Galloy on 09/12/16.
 */
public final class Sleeper {

    private static final long ONE_HOUR = 3_600 * 1000;
    private final long sleeperCreationMillis = System.currentTimeMillis();
    private long sleepingTimeMillis;

    /**
     * Constructor.
     *
     * @param defaultSleepingTimeMillis the default first sleeping timer
     */
    public Sleeper(long defaultSleepingTimeMillis) {
        sleepingTimeMillis = defaultSleepingTimeMillis;
    }

    /**
     * Sleep and increment the internal time.
     */
    public void sleepAndIncrementTimer() {
        sleepSilently();
        sleepingTimeMillis = Math.min(ONE_HOUR, 2 * sleepingTimeMillis);
    }

    /**
     * Get the total sleeper life time.
     *
     * @return the life time in millis
     */
    public long totalExectutionTimeMillis() {
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
