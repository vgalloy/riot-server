package vgalloy.riot.server.service.api.model.wrapper;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by Vincent Galloy on 29/12/16.
 *
 * @author Vincent Galloy
 */
public final class ResourceWrapper<T> extends AbstractResourceWrapper<T> {

    private static final ResourceWrapper<?> NOT_LOADED = new ResourceWrapper<>(ResourceStatus.NOT_LOADED, null);
    private static final ResourceWrapper<?> DOES_NOT_EXIST = new ResourceWrapper<>(ResourceStatus.DOES_NOT_EXIST, null);

    /**
     * Constructor.
     *
     * @param resourceStatus the resource status
     * @param resource       the resource
     */
    public ResourceWrapper(ResourceStatus resourceStatus, T resource) {
        super(resourceStatus, resource);
    }

    /**
     * Build a Present resource wrapper.
     *
     * @param resource the resource
     * @param <TYPE>   the parameter type
     * @return the wrapper
     */
    public static <TYPE> ResourceWrapper<TYPE> of(TYPE resource) {
        Objects.requireNonNull(resource);
        return new ResourceWrapper<>(ResourceStatus.PRESENT, resource);
    }

    /**
     * Build a not loaded resource wrapper.
     *
     * @param <TYPE> the parameter type
     * @return the wrapper
     */
    @SuppressWarnings("unchecked")
    public static <TYPE> ResourceWrapper<TYPE> notLoaded() {
        return (ResourceWrapper<TYPE>) NOT_LOADED;
    }

    /**
     * Build a non existing resource wrapper.
     *
     * @param <TYPE> the parameter type
     * @return the wrapper
     */
    @SuppressWarnings("unchecked")
    public static <TYPE> ResourceWrapper<TYPE> doesNotExist() {
        return (ResourceWrapper<TYPE>) DOES_NOT_EXIST;
    }

    /**
     * Throw an exception if the resource is not loaded.
     *
     * @param supplier the exception supplier
     * @return a {@link PresentOrDoesNotExistResourceWrapper}
     */
    public PresentOrDoesNotExistResourceWrapper<T> ifNotLoadedThrow(Supplier<RuntimeException> supplier) {
        Objects.requireNonNull(supplier);

        if (ResourceStatus.NOT_LOADED == resourceStatus) {
            throw supplier.get();
        }
        return new PresentOrDoesNotExistResourceWrapper<>(resourceStatus, resource);
    }
}
