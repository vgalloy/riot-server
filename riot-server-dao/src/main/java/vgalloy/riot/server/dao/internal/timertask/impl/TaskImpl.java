package vgalloy.riot.server.dao.internal.timertask.impl;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vgalloy.riot.server.dao.internal.timertask.Task;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 27/08/16.
 */
public class TaskImpl extends TimerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskImpl.class);

    private final Task task;
    private final Timer timer;
    private final int periodAfterEnded;

    /**
     * Constructor.
     *
     * @param task             the task to execute
     * @param timer            the time
     * @param periodAfterEnded the sleeping time between to execution. The time start after the end of the previous
     *                         execution. This time must be in millis sec.
     */
    public TaskImpl(Task task, Timer timer, int periodAfterEnded) {
        this.task = Objects.requireNonNull(task);
        this.timer = Objects.requireNonNull(timer);
        this.periodAfterEnded = periodAfterEnded;
        timer.schedule(this, periodAfterEnded);
    }

    @Override
    public void run() {
        try {
            task.execute();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage(), e);
        }
        new TaskImpl(task, timer, periodAfterEnded);
    }
}
