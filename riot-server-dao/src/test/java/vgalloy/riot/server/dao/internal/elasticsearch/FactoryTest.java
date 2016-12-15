package vgalloy.riot.server.dao.internal.elasticsearch;

import java.time.LocalDateTime;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import vgalloy.riot.server.dao.internal.elasticsearch.factory.ElasticsearchClientFactory;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author Vincent Galloy - 16/12/16
 *         Created by Vincent Galloy on 16/12/16.
 */
//@ESIntegTestCase.ClusterScope(scope= ESIntegTestCase.Scope.SUITE, numDataNodes=1)
public class FactoryTest /*extends ESIntegTestCase*/ {

    //
    //    @Override
    //    protected Settings nodeSettings(int nodeOrdinal) {
    //        return Settings.builder().put(super.nodeSettings(nodeOrdinal))
    //                .build();
    //    }

    @Test
    public void simpleTest() {

        // TODO : gerer les log LOG4J
        // TODO : test qui couvre les deux bases
        TransportClient client = ElasticsearchClientFactory.getClient();

        try {
            IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("user", "kimchy")
                            .field("postDate", LocalDateTime.now())
                            .field("message", "trying out Elasticsearch")
                            .endObject()
                    )
                    .get();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
