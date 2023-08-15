package elastic.client;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;

class IndexDocumentTest {
    @Test
    @Disabled("Not work")
    void emptySettings() throws IOException {
        var settings = Settings.builder()
                .put("xpack.security.user", "elastic:changeme").build();
        var client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300)

                );

//        XContentBuilder document = jsonBuilder()
//                .startObject()
//                .field("user", "elastic")
//                .field("password", "changeme")
//                .field("postDate", new Date())
//                .field("message", "trying out Elasticsearch")
////                .field("network.host", "localhost")
//                .endObject();
        var response = client.prepareIndex("twitter", "tweet", "1")
                .setSource("{\"person\": \"John\"}", XContentType.JSON)
                .get();
        System.out.println("Response: " + response);
        client.close();
    }

    @Test
    void settings() {
        var settings = Settings.builder()
                .put("cluster.name", "myClusterName").build();
        var client = new PreBuiltTransportClient(settings);
        client.close();
    }
}
