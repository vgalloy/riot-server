package vgalloy.riot.server.service.api.model;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/06/16.
 */
public class LoaderInformation {

    private final long startTime;

    private long totalRequestNumber;
    private long rankedStatsRequestNumber;
    private boolean isRunning;

    /**
     * Constructor.
     */
    public LoaderInformation() {
        startTime = System.currentTimeMillis();
        isRunning = true;
    }

    /**
     * Get the execution time in millis.
     *
     * @return the execution time
     */
    public long getExecutionTime() {
        return System.currentTimeMillis() - startTime;
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
     * Get the information as a string.
     *
     * @return the information as string.
     */
    public String printInformation() {
        return rankedStatsRequestNumber + "/" + totalRequestNumber + "(" + rankedStatsRequestNumber * 100 / totalRequestNumber + "%)" + " in " + getExecutionTime() / 1000 + " sec (" + getExecutionTime() / totalRequestNumber + "sec/request)";
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
     *
     * @param running true if the loader is running
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }
}
