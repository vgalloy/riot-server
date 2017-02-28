package vgalloy.riot.server.webservice.internal.model;

import java.util.Objects;

/**
 * Created by Vincent Galloy on 14/09/16.
 *
 * @author Vincent Galloy
 */
public final class Error {

    private final int code;
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the error code
     * @param message the message
     */
    public Error(int code, String message) {
        this.code = Objects.requireNonNull(code);
        this.message = Objects.requireNonNull(message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
