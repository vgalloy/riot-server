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

    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link UserException} if it is.
     *
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param <T>     the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws UserException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new UserException(message);
        }
        return obj;
    }
}
