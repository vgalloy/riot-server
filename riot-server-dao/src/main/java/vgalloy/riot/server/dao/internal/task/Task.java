package vgalloy.riot.server.dao.internal.task;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public interface Task {

    /**
     * The process for periodical execution.
     */
    void execute();
}