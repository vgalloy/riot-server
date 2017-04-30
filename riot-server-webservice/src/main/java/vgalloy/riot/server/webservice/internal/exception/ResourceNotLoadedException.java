package vgalloy.riot.server.webservice.internal.exception;

/**
 * Created by Vincent Galloy on 29/12/16.
 *
 * @author Vincent Galloy
 */
public final class ResourceNotLoadedException extends RuntimeException {

    public static final String MESSAGE = "The resource will be loaded soon. Retry later";
    private static final long serialVersionUID = 8185951433747766959L;
}
