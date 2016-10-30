package vgalloy.riot.server.loader.internal.executor.model;

import java.util.Objects;

import vgalloy.riot.api.api.query.Query;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 04/07/16.
 */
public class Request<DTO> {

    private final Query<DTO> query;
    private final int priority;

    /**
     * Constructor.
     *
     * @param query    the query
     * @param priority the priority of the query
     */
    public Request(Query<DTO> query, int priority) {
        this.query = Objects.requireNonNull(query, "query can not be null");
        this.priority = priority;
    }

    public Query<DTO> getQuery() {
        return query;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Request{" +
                "query=" + query +
                ", priority=" + priority +
                '}';
    }
}
