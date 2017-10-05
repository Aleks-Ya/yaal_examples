package elastic.client;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;

public class InitClient {
    @Test
    public void initAndClose() throws IOException {
        RestClient client = RestClient.builder(
                new HttpHost("host", 1111, "http")
        ).build();
        client.close();
    }

    @Test
    public void setHeaders() throws IOException {
        Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
        RestClient client = RestClient.builder(
                new HttpHost("host", 1111, "http")
        ).setDefaultHeaders(defaultHeaders).build();
        client.close();
    }

}
