package vgalloy.riot.server.service.api.model.wrapper;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Vincent Galloy - 29/12/16
 *         Created by Vincent Galloy on 29/12/16.
 */
public class PresentOrDoesNotExistResourceWrapper<T> extends AbstractResourceWrapper<T> {

    /**
     * Constructor.
     *
     * @param resourceStatus the resource status
     * @param resource       the resource
     */
    PresentOrDoesNotExistResourceWrapper(ResourceStatus resourceStatus, T resource) {
        super(resourceStatus, resource);
    }

    /**
     * Throw an exception if the resource is doesn't exist.
     *
     * @param supplier the exception supplier
     * @return the resource
     */
    public T ifDoesNotExistThrow(Supplier<RuntimeException> supplier) {
        Objects.requireNonNull(supplier);
        if (ResourceStatus.DOES_NOT_EXIST == resourceStatus) {
            throw supplier.get();
        }
        return resource;
    }
}
