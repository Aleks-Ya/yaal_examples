package elastic.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClientBuilder;

/**
 * Connect to local ElasticSearch that run by Building+/Docker+/DockerImage+/Application+/ElasticSearch.
 */
final class SecurityHelper {
    static final RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback;
    static final String HOST = "localhost";
    static final int PORT = 9200;
    static final String SCHEMA = "http";
    static final String USER_NAME = "elastic";
    static final String PASSWORD = "changeme";

    static {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(USER_NAME, PASSWORD));
        httpClientConfigCallback = builder -> builder.setDefaultCredentialsProvider(credentialsProvider);
    }
}
