package vgalloy.riot.server.loader.internal.factory.supplier;

import java.util.Objects;
import java.util.function.Supplier;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import vgalloy.riot.server.loader.api.service.exception.LoaderException;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 03/12/16.
 */
public final class ConfigurationSupplier implements Supplier<Configuration> {

    private final String configurationName;

    /**
     * Constructor.
     *
     * @param configurationName the configuration name
     */
    public ConfigurationSupplier(String configurationName) {
        this.configurationName = Objects.requireNonNull(configurationName);
    }

    @Override
    public Configuration get() {
        try {
            return new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName(configurationName))
                    .getConfiguration();
        } catch (ConfigurationException e) {
            throw new LoaderException("Unable to load configuration", e);
        }
    }
}
