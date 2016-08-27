package vgalloy.riot.server.dao.internal.exception;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/07/16.
 */
public class MongoDaoException extends RuntimeException {

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
}
