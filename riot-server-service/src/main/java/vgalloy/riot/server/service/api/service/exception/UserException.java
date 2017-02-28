package vgalloy.riot.server.service.api.service.exception;

/**
 * Created by Vincent Galloy on 07/10/16.
 * This exception should be throw when the use send wrong input.
 * Be aware : the message will be display to the user.
 *
 * @author Vincent Galloy
 */
public final class UserException extends ServiceException {

    private static final long serialVersionUID = -3470523490729510231L;

    /**
     * Constructor.
     *
     * @param message the detail message.
     */
    public UserException(String message) {
        super(message);
    }
}
