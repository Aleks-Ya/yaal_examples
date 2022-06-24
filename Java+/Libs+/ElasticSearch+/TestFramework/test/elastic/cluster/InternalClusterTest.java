package elastic.cluster;

import org.assertj.core.api.Assertions;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.test.InternalTestCluster;
import org.elasticsearch.test.NodeConfigurationSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Disabled("Not working")
public class InternalClusterTest {

    @Test
    public void indexDocument() throws IOException, InterruptedException {
        var clusterSeed = 1L;
        var baseDir = Files.createTempDirectory(getClass().getSimpleName());
        var randomlyAddDedicatedMasters = false;
        var autoManageMasterNodes = false;
        var minNumDataNodes = 1;
        var maxNumDataNodes = 1;
        var clusterName = "the_cluster";
        var nodeConfigurationSource = NodeConfigurationSource.EMPTY;
        var numClientNodes = 1;
        var nodePrefix = "node-";
        Collection<Class<? extends Plugin>> mockPlugins = List.of();
        Function<Client, Client> clientWrapper = client -> client;

        System.setProperty("tests.security.manager", "false");
        var cluster = new InternalTestCluster(clusterSeed, baseDir, randomlyAddDedicatedMasters,
                autoManageMasterNodes, minNumDataNodes, maxNumDataNodes, clusterName, nodeConfigurationSource,
                numClientNodes, nodePrefix, mockPlugins, clientWrapper);

        cluster.beforeTest(new Random(), 1D);
        Client client = cluster.client();
        indexRequest(client);
        cluster.afterTest();
    }

    private void indexRequest(Client client) {
        var indexName = "the_index";
        var indexRequest = new IndexRequest(indexName);
        var id = "1";
        indexRequest.id(id);
        var jsonString = "{" +
                "\"user\":\"John\"," +
                "\"postDate\":\"2021-01-30\"," +
                "\"message\":\"trying out ES\"" +
                "}";
        indexRequest.source(jsonString, XContentType.JSON);

        var indexResponse = client.index(indexRequest).actionGet();
        var status = indexResponse.status();
        var statusCode = status.getStatus();
        Assertions.assertThat(statusCode).isEqualTo(201);

        var getRequest = new GetRequest(indexName, id);
        var getResponse = client.get(getRequest).actionGet();
        var source = getResponse.getSource();
        Assertions.assertThat(source.toString()).isEqualTo("{postDate=2021-01-30, message=trying out ES, user=John}");
    }
}
