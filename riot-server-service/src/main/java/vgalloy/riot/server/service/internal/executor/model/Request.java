package vgalloy.riot.server.service.internal.executor.model;

import vgalloy.riot.api.api.query.Query;

import java.util.Objects;

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
}
