package vgalloy.riot.server.dao.internal.common.proxy;

import java.util.Objects;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class ExecutionResponse {

    private final ExecutionStatus executionStatus;
    private final Object result;

    /**
     * Constructor.
     *
     * @param executionStatus the execution status
     * @param result          the result
     */
    private ExecutionResponse(ExecutionStatus executionStatus, Object result) {
        this.executionStatus = Objects.requireNonNull(executionStatus);
        this.result = result;
    }

    /**
     * Build unsupported response.
     *
     * @return the ExecutionResponse
     */
    public static ExecutionResponse unsupported() {
        return new ExecutionResponse(ExecutionStatus.UNSUPPORTED, null);
    }

    /**
     * Build throwable response.
     *
     * @param throwable the cause
     * @return the ExecutionResponse
     */
    public static ExecutionResponse throwable(Throwable throwable) {
        return new ExecutionResponse(ExecutionStatus.THROWABLE, throwable);
    }

    /**
     * Build correct response.
     *
     * @param result the result
     * @return the ExecutionResponse
     */
    public static ExecutionResponse success(Object result) {
        return new ExecutionResponse(ExecutionStatus.SUCCESS, result);
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    /**
     * Obtains the response object.
     *
     * @return the response
     */
    public Object get() {
        if (executionStatus == ExecutionStatus.UNSUPPORTED) {
            throw new IllegalStateException("Can not obtain result from unsupported operation");
        }
        return result;
    }

    public enum ExecutionStatus {
        SUCCESS,
        UNSUPPORTED,
        THROWABLE
    }
}
