package vgalloy.riot.server.dao.internal.task;

/**
 * Created by Vincent Galloy on 27/08/16.
 *
 * @author Vincent Galloy
 */
public interface Task {

    /**
     * The process for periodical execution.
     */
    void execute();
}
