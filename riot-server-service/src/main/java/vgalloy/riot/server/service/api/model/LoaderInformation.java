package vgalloy.riot.server.service.api.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 */
public class LoaderInformation {

    private final Instant startTime;

    private Instant endTime;
    private long totalRequestNumber;
    private boolean isRunning;

    /**
     * Constructor.
     */
    public LoaderInformation() {
        startTime = Instant.now();
        isRunning = true;
    }

    /**
     * Get the execution time in millis.
     *
     * @return the execution time
     */
    public long getExecutionTimeMillis() {
        if (endTime != null) {
            return ChronoUnit.MILLIS.between(startTime, endTime);
        }
        return ChronoUnit.MILLIS.between(startTime, Instant.now());
    }

    /**
     * Get the total request send.
     *
     * @return the request number
     */
    public long getTotalRequestNumber() {
        return totalRequestNumber;
    }

    /**
     * Add a new request.
     */
    public void addRequest() {
        totalRequestNumber++;
    }

    /**
     * Get the status of the loader.
     *
     * @return true if the loader is running
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Set the status of the loader.
     */
    public void finish() {
        isRunning = false;
        endTime = Instant.now();
    }
}
