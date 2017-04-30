package vgalloy.riot.server.webservice.internal.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Vincent Galloy on 14/09/16.
 *
 * @author Vincent Galloy
 */
public final class ErrorDto implements Serializable {

    private static final long serialVersionUID = 8278940929567627860L;

    private final int code;
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the error code
     * @param message the message
     */
    public ErrorDto(int code, String message) {
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
