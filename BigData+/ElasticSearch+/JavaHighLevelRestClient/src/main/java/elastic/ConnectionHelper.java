package elastic;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Connect to local ElasticSearch that run by Building+/Docker+/DockerImage+/Application+/ElasticSearch.
 */
public final class ConnectionHelper {
    private static final String HOST = "localhost";
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";
    private static final String USER_NAME = "elastic";
    private static final String PASSWORD = "changeme";
    private static final RestClient lowLevelRestClient;
    private static final RestHighLevelClient higLevelRestClient;

    static {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USER_NAME, PASSWORD));
        HttpHost httpHost = new HttpHost(HOST, PORT, SCHEMA);
        lowLevelRestClient = RestClient.builder(httpHost)
                .setHttpClientConfigCallback(builder -> builder.setDefaultCredentialsProvider(credentialsProvider))
                .build();
        higLevelRestClient = new RestHighLevelClient(lowLevelRestClient);
    }

    private ConnectionHelper() {
    }

    public static RestClient getLowLevelRestClient() {
        return lowLevelRestClient;
    }

    public static RestHighLevelClient getHighLevelRestClient() {
        return higLevelRestClient;
    }
}
