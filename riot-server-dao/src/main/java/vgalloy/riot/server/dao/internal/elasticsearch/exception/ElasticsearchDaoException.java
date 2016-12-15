package vgalloy.riot.server.dao.internal.elasticsearch.exception;

import vgalloy.riot.server.dao.api.exception.AbstractDaoException;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public class ElasticsearchDaoException extends AbstractDaoException {

    private static final long serialVersionUID = -7290851517566341744L;

    /**
     * Constructor.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ElasticsearchDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
