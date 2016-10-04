package vgalloy.riot.server.dao.internal.exception;

import vgalloy.riot.server.dao.api.exception.DaoException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public class MongoDaoException extends DaoException {

    public static final String UNABLE_TO_SAVE_THE_DTO = "Unable to save the dto";

    private static final long serialVersionUID = -6622631875525803695L;

    /**
     * Constructor.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public MongoDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message the detail message
     */
    public MongoDaoException(String message) {
        super(message);
    }
}
