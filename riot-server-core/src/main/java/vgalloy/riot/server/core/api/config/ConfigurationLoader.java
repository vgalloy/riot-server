package vgalloy.riot.server.core.api.config;

import java.util.Optional;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vincent Galloy on 24/03/17.
 *
 * @author Vincent Galloy
 */
public final class ConfigurationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);
    private static final String CONFIGURATION_FILE_NAME = "application.properties";
    private static final String OPTIONAL_CONFIGURATION_FOLDER = "config";

    /**
     * Try to load the configuration at the {@code CONFIGURATION_FILE_NAME} location. If the file is not present try to find the location
     * at {@code ./config/CONFIGURATION_FILE_NAME} location.
     *
     * @return the configuration
     * @throws ConfigurationException if both configuration can not be loaded
     */
    public static Configuration loadConfiguration() throws ConfigurationException {
        Optional<Configuration> defaultConfiguration = internalLoadConfiguration(CONFIGURATION_FILE_NAME);
        if (defaultConfiguration.isPresent()) {
            return defaultConfiguration.get();
        }
        return internalLoadConfiguration(OPTIONAL_CONFIGURATION_FOLDER + "/" + CONFIGURATION_FILE_NAME)
                .orElseThrow(() -> new ConfigurationException("Unable to load configuration"));
    }

    /**
     * Obtains the {@link Configuration}.
     *
     * @param fileName the configuration file name
     * @return the configuration
     */
    private static Optional<Configuration> internalLoadConfiguration(String fileName) {
        LOGGER.info("Try to load the configuration file : {}", fileName);
        try {
            return Optional.ofNullable(new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName(fileName))
                    .getConfiguration());
        } catch (ConfigurationException e) {
            return Optional.empty();
        }
    }
}
