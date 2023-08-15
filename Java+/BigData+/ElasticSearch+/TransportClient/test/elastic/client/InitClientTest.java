package elastic.client;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

class InitClientTest {
    @Test
    void emptySettings() throws UnknownHostException {
        var client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
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
