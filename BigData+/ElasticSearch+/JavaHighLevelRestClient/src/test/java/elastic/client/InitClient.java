package elastic.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

public class InitClient {
    @Test
    public void emptySettings() throws IOException {
        RestClient lowLevelRestClient = RestClient.builder(
                new HttpHost("localhost", 9201, "http")
        ).build();

        RestHighLevelClient client =
                new RestHighLevelClient(lowLevelRestClient);

        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");

        GetResponse response = client.get(getRequest);
        System.out.println(response);

    }

}
