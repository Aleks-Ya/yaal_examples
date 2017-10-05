package elastic.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InitClient {
    @Test
    public void emptySettings() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
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
