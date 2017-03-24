package vgalloy.riot.server.dao.api.exception;

/**
 * Created by Vincent Galloy on 05/09/16.
 *
 * @author Vincent Galloy
 */
public abstract class AbstractDaoException extends RuntimeException {

    private static final long serialVersionUID = -5421054849724377785L;

    /**
     * Constructor.
     *
     * @param message the detail message
     */
    public AbstractDaoException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public AbstractDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause   the cause
     */
    public AbstractDaoException(Throwable cause) {
        super(cause);
    }
}
