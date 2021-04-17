package elastic;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;
import java.util.Random;

/**
 * Connect to local ElasticSearch that run by Building+/Docker+/DockerImage+/Application+/ElasticSearch.
 */
public final class EsHelper {
    private static final String HOST = "localhost";
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";
    private static final String USER_NAME = "elastic";
    private static final String PASSWORD = "changeme";
    private static final RestHighLevelClient highLevelRestClient;
    private static final Random random = new Random();

    static {
        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USER_NAME, PASSWORD));
        var httpHost = new HttpHost(HOST, PORT, SCHEMA);
        var lowLevelRestClientBuilder = RestClient.builder(httpHost)
                .setHttpClientConfigCallback(builder -> builder.setDefaultCredentialsProvider(credentialsProvider));
        highLevelRestClient = new RestHighLevelClient(lowLevelRestClientBuilder);
    }

    private EsHelper() {
    }

    public static RestHighLevelClient getHighLevelRestClient() {
        return highLevelRestClient;
    }

    public static String createRandomIndexName() {
        return String.format("%s-%d", EsHelper.class.getSimpleName(), random.nextInt(Integer.MAX_VALUE)).toLowerCase();
    }

    public static CreateIndexResponse createIndex(String indexName) {
        try {
            var request = new CreateIndexRequest(indexName);
            return getHighLevelRestClient().indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isIndexExist(String indexName) {
        try {
            var getIndexRequest = new GetIndexRequest(indexName);
            return getHighLevelRestClient().indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AcknowledgedResponse deleteIndex(String indexName) {
        try {
            var deleteIndexRequest = new DeleteIndexRequest(indexName);
            return getHighLevelRestClient().indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
