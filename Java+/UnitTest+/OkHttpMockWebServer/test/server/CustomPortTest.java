package server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import util.NetUtil;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPortTest {

    @Test
    void port() throws IOException {
        try (var server = new MockWebServer()) {
            var expBody = "message body";
            server.enqueue(new MockResponse().setBody(expBody));

            var port = 39031;
            server.start(port);

            var baseUrl = server.url("/v1/chat/");
            assertThat(baseUrl.port()).isEqualTo(port);

            var body = NetUtil.urlContentToString(new URL(baseUrl.url(), "messages/"));
            assertThat(body).isEqualTo(expBody);
        }
    }
}