package es7.low;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

/**
 * Connect to local ElasticSearch that run by Building+/Docker+/DockerImage+/Application+/ElasticSearch.
 */
public final class SecurityHelper {
    private static final String HOST = "localhost";
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";
    private static final String USER_NAME = "es7";
    private static final String PASSWORD = "changeme";
    private static final HttpHost HTTP_HOST = new HttpHost(HOST, PORT, SCHEMA);
    private static final RestClientBuilder.HttpClientConfigCallback HTTP_CLIENT_CONFIG_CALLBACK;

    static {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USER_NAME, PASSWORD));
        HTTP_CLIENT_CONFIG_CALLBACK = builder -> builder.setDefaultCredentialsProvider(credentialsProvider);
    }

    public static RestClientBuilder newRestClientBuilder() {
        return RestClient.builder(HTTP_HOST)
                .setHttpClientConfigCallback(HTTP_CLIENT_CONFIG_CALLBACK);
    }

    public static RestClient getLowLevelRestClient() {
        return newRestClientBuilder().build();
    }
}
