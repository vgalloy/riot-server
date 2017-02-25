package vgalloy.riot.server.webservice.internal.model;

/**
 * @author Vincent Galloy - 29/12/16
 *         Created by Vincent Galloy on 29/12/16.
 */
public final class ResourceDoesNotExistException extends RuntimeException {

    private static final long serialVersionUID = -6310069484240866471L;
    public static final String MESSAGE = "Resource doesn't exist";
}
