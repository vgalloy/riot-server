package com.vgalloy.riot.server.loader.internal.factory.supplier;

import java.util.Objects;
import java.util.function.Supplier;

import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vgalloy.riot.server.loader.api.service.LoaderClient;
import com.vgalloy.riot.server.loader.internal.client.impl.FakeLoaderClientImpl;
import com.vgalloy.riot.server.loader.internal.client.impl.LoaderClientImpl;

/**
 * Created by Vincent Galloy on 03/12/16.
 *
 * @author Vincent Galloy
 */
public final class LoaderClientSupplier implements Supplier<LoaderClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoaderClientSupplier.class);

    private final ConnectionFactory connectionFactory;

    /**
     * Constructor.
     *
     * @param connectionFactory the connection factory
     */
    public LoaderClientSupplier(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
    }

    @Override
    public LoaderClient get() {
        try {
            return new LoaderClientImpl(connectionFactory);
        } catch (Exception e) {
            LOGGER.warn("Unable to start client", e.getMessage());
            return new FakeLoaderClientImpl();
        }
    }
}
