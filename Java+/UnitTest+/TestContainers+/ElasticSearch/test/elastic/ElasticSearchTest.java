package elastic;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ElasticSearchTest {
    private static final String ELASTICSEARCH_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch:7.12.0";
    private static final String ELASTICSEARCH_USERNAME = "elastic";
    private static final String ELASTICSEARCH_PASSWORD = "changeme";

    @Test
    public void health() throws IOException {
        try (var container = new ElasticsearchContainer(ELASTICSEARCH_IMAGE)) {
            container.start();

            var credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(ELASTICSEARCH_USERNAME, ELASTICSEARCH_PASSWORD));

            var client = RestClient.builder(HttpHost.create(container.getHttpHostAddress()))
                    .setHttpClientConfigCallback(builder -> builder.setDefaultCredentialsProvider(credentialsProvider))
                    .build();

            var response = client.performRequest(new Request("GET", "/_cluster/health"));

            var statusCode = response.getStatusLine().getStatusCode();
            assertThat(statusCode, equalTo(200));
        }
    }
}
