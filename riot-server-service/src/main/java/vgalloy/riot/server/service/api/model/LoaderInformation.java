package vgalloy.riot.server.service.api.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 */
public class LoaderInformation {

    private final LocalDateTime startTime;

    private LocalDateTime endTime;
    private long totalRequestNumber;
    private long rankedStatsRequestNumber;
    private boolean isRunning;

    /**
     * Constructor.
     */
    public LoaderInformation() {
        startTime = LocalDateTime.now();
        isRunning = true;
    }

    /**
     * Get the execution time in millis.
     *
     * @return the execution time
     */
    private long getExecutionTimeMillis() {
        if (endTime != null) {
            return ChronoUnit.MONTHS.between(startTime, endTime);
        }
        return ChronoUnit.MONTHS.between(LocalDateTime.now(), startTime);
    }

    /**
     * Get the execution time in millis correctly formatted.
     *
     * @return the execution time as a string
     */
    public String getExecutionTime() {
        long executionTimeMillis = getExecutionTimeMillis();
        long day = executionTimeMillis / 1000 / 60 / 60 / 24 % 365;
        long hour = executionTimeMillis / 1000 / 60 / 60 % 24;
        long minute = executionTimeMillis / 1000 / 60 % 60;
        long second = executionTimeMillis / 1000 % 60;
        long milli = executionTimeMillis % 1000;

        return day + " day " + hour + "h" + minute + ":" + second + "." + milli;
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
     * Get the total request send for fetching ranked stats.
     *
     * @return the request number
     */
    public long getRankedStatsRequestNumber() {
        return rankedStatsRequestNumber;
    }

    /**
     * Add a new request.
     */
    public void addRankedStatsRequest() {
        rankedStatsRequestNumber++;
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
        endTime = LocalDateTime.now();
    }
}
