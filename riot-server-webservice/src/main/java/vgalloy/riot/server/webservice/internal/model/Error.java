package vgalloy.riot.server.webservice.internal.model;

/**
 * @author Vincent Galloy - 14/09/16
 *         Created by Vincent Galloy on 14/09/16.
 */
public class Error {

    private final int code;
    private final String message;

    /**
     * Constructor.
     * @param code the error code
     * @param message the message
     */
    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
