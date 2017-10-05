package elastic.client;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class IndexDocument {
    @Test
    public void emptySettings() throws IOException {
        Settings settings = Settings.builder()
                .put("xpack.security.user", "elastic:changeme").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300)

                );

//        XContentBuilder document = jsonBuilder()
//                .startObject()
//                .field("user", "elastic")
//                .field("password", "changeme")
//                .field("postDate", new Date())
//                .field("message", "trying out Elasticsearch")
////                .field("network.host", "localhost")
//                .endObject();
        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource("{\"person\": \"John\"}", XContentType.JSON)
                .get();
        System.out.println("Response: " + response);
        client.close();
    }

    @Test
    public void settings() {
        Settings settings = Settings.builder()
                .put("cluster.name", "myClusterName").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.close();
    }
}
