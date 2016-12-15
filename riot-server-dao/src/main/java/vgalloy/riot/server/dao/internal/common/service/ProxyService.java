package vgalloy.riot.server.dao.internal.common.service;

import vgalloy.riot.server.dao.internal.common.proxy.ExecutionResponse;

/**
 * @author Vincent Galloy - 19/12/16
 *         Created by Vincent Galloy on 19/12/16.
 */
public interface ProxyService {

    enum Database {
        MONGO,
        ELASTICSEARCH
    }

    /**
     * Set the database to use.
     *
     * @param database the database
     */
    void set(Database database);

    /**
     * Return the result from the selected database.
     *
     * @param mongoResult         the mongo result
     * @param elasticsearchResult the elastic search result
     * @return the result
     */
    Object choose(ExecutionResponse mongoResult, ExecutionResponse elasticsearchResult);
}
