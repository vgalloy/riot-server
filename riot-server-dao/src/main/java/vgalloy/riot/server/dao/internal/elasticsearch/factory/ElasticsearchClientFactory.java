package vgalloy.riot.server.dao.internal.elasticsearch.factory;

import java.net.InetAddress;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import vgalloy.riot.server.dao.internal.elasticsearch.exception.ElasticsearchDaoException;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
public final class ElasticsearchClientFactory {

    private static final TransportClient CLIENT;

    static {
        try {
            Configuration configuration = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                    .configure(new Parameters().properties().setFileName("application.properties"))
                    .getConfiguration();
            String elasticHost = configuration.getString("es.host");
            Integer elasticPort = configuration.getInt("es.port");

            CLIENT = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticHost), elasticPort));
        } catch (Exception e) {
            throw new ElasticsearchDaoException("Unable to load elasticsearch configuration", e);
        }
    }

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ElasticsearchClientFactory() {
        throw new AssertionError();
    }

    /**
     * Get the elasticsearch client.
     *
     * @return the client
     */
    public static TransportClient getClient() {
        return CLIENT;
    }
}
