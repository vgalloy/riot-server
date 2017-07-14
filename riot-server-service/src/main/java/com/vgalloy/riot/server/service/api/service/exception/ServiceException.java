package com.vgalloy.riot.server.service.api.service.exception;

/**
 * Created by Vincent Galloy on 05/07/16.
 *
 * @author Vincent Galloy
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -3574105229077994452L;

    /**
     * Constructor.
     *
     * @param message the detail message.
     */
    public ServiceException(String message) {
        super(message);
    }
}
