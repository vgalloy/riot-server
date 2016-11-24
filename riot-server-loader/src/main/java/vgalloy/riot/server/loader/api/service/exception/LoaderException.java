package vgalloy.riot.server.loader.api.service.exception;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 30/10/16.
 */
public class LoaderException extends RuntimeException {

    private static final long serialVersionUID = -8539678131000703374L;

    /**
     * Constructor.
     *
     * @param message the detail message.
     */
    public LoaderException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause the cause
     */
    public LoaderException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message the detail message.
     * @param cause   the cause
     */
    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
