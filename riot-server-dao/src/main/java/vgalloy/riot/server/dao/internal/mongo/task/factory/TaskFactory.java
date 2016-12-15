package vgalloy.riot.server.dao.internal.mongo.task.factory;

import java.util.Timer;

import vgalloy.riot.server.dao.internal.mongo.task.Task;
import vgalloy.riot.server.dao.internal.mongo.task.impl.TaskImpl;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public final class TaskFactory {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private TaskFactory() {
        throw new AssertionError();
    }

    /**
     * Build a new {@link java.util.TimerTask}.
     *
     * @param task                   the task to for repeated execution
     * @param periodAfterEndedMillis the sleeping time between to execution. The time start after the end of the previous
     *                               execution. This time must be in millis sec.
     */
    public static void startTask(Task task, int periodAfterEndedMillis) {
        Timer timer = new Timer();
        timer.schedule(new TaskImpl(task, timer, periodAfterEndedMillis), 0);
    }
}
