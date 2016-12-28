package vgalloy.riot.server.dao.api.exception;

/**
 * @author Vincent Galloy - 28/12/16
 *         Created by Vincent Galloy on 28/12/16.
 */
public final class MatchDetailConversionException extends Exception {

    private static final long serialVersionUID = -3044238983820043020L;

    /**
     * Constructor.
     *
     * @param message message   the detail message
     */
    public MatchDetailConversionException(String message) {
        super(message);
    }
}
