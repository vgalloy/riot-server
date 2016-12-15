package vgalloy.riot.server.dao.internal.common.service.impl;

import java.util.Objects;

import vgalloy.riot.server.dao.internal.common.proxy.ExecutionResponse;
import vgalloy.riot.server.dao.internal.common.service.ProxyService;

/**
 * @author Vincent Galloy - 19/12/16
 *         Created by Vincent Galloy on 19/12/16.
 */
public enum  ProxyServiceImpl implements ProxyService {
    INSTANCE;

    private Database database = Database.MONGO;

    @Override
    public void set(Database database) {
        this.database = Objects.requireNonNull(database);
    }

    @Override
    public Object choose(ExecutionResponse mongoResult, ExecutionResponse elasticsearchResult) {
        switch (database) {
            case MONGO:
                return mongoResult.get();
            case ELASTICSEARCH:
                return elasticsearchResult.get();
            default:
                throw new IllegalStateException();
        }
    }
}
