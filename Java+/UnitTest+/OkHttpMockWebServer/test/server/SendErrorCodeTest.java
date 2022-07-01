package server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import util.NetUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SendErrorCodeTest {
    @Test
    void setResponseCode() throws IOException {
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setResponseCode(418));
            server.start();
            var baseUrl = server.url("/v1/chat/");
            assertThatThrownBy(() -> NetUtil.urlContentToString(baseUrl.uri()))
                    .isInstanceOf(RuntimeException.class)
                    .cause().isInstanceOf(IOException.class)
                    .hasMessageStartingWith("Server returned HTTP response code: 418 for URL:");
        }
    }
}
