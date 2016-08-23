package vgalloy.riot.server.service.api.service.exception;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 05/07/16.
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -3574105229077994452L;

    /**
     * Constructor.
     *
     * @param message the detail message.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
