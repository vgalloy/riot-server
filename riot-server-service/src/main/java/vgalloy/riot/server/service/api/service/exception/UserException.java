package vgalloy.riot.server.service.api.service.exception;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 07/10/16.
 *         This exception should be throw when the use send wrong input
 */
public class UserException extends ServiceException {

    /**
     * Constructor.
     *
     * @param message the detail message.
     */
    public UserException(String message) {
        super(message);
    }
}
