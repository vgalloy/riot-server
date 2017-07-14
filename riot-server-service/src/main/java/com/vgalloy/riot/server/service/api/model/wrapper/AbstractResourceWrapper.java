package com.vgalloy.riot.server.service.api.model.wrapper;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by Vincent Galloy on 29/12/16.
 *
 * @author Vincent Galloy
 */
public abstract class AbstractResourceWrapper<T> {

    protected final ResourceStatus resourceStatus;
    protected final T resource;

    /**
     * Constructor.
     *
     * @param resourceStatus the resource status
     * @param resource       the resource
     */
    protected AbstractResourceWrapper(ResourceStatus resourceStatus, T resource) {
        this.resourceStatus = resourceStatus;
        this.resource = resource;
    }

    /**
     * Execute consumer if resource is present.
     *
     * @param consumer the consumer
     */
    public void ifPresent(Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        if (ResourceStatus.PRESENT == resourceStatus) {
            consumer.accept(resource);
        }
    }

    protected enum ResourceStatus {
        PRESENT,
        NOT_LOADED,
        DOES_NOT_EXIST
    }
}
