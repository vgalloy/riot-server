package vgalloy.riot.server.webservice.internal.model;

/**
 * @author Vincent Galloy - 29/12/16
 *         Created by Vincent Galloy on 29/12/16.
 */
public final class ResourceNotLoadedException extends RuntimeException {

    private static final long serialVersionUID = 8185951433747766959L;
    public static final String MESSAGE = "The resource will be loaded soon. Retry later";
}
