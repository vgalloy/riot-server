package vgalloy.riot.server.loader.internal.executor.helper;

/**
 * @author Vincent Galloy - 09/12/16
 *         Created by Vincent Galloy on 09/12/16.
 */
public final class CycleManager {

    private static final long DEFAULT_SLEEPING_TIME_MILLIS = 2_000;
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
            throw new RuntimeException(e1);
        }
    }

    /**
     * Decide if the request should be try again. And increment iteration.
     *
     * @return false if the request should not be try again
     */
    public boolean tryAgain() {
        iteration++;
        sleepingTimeMillis = Math.min(60 * ONE_MINUTE, 2 * sleepingTimeMillis);
        return iteration <= 20;
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
