package server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import util.NetUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldTest {

    @Test
    void mock() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body1 = "hello, world!";
            var body2 = "sup, bra?";
            var body3 = "yo dog";
            server.enqueue(new MockResponse().setBody(body1));
            server.enqueue(new MockResponse().setBody(body2));
            server.enqueue(new MockResponse().setBody(body3));

            server.start();

            var baseUrl = server.url("/v1/chat/");

            var body = NetUtil.urlContentToString(baseUrl.uri().resolve("messages/"));
            assertThat(body).isEqualTo(body1);

            var actBody2 = NetUtil.urlContentToString(baseUrl.uri().resolve("messages/2"));
            assertThat(actBody2).isEqualTo(body2);

            var actBody3 = NetUtil.urlContentToString(baseUrl.uri().resolve("messages/3"));
            assertThat(actBody3).isEqualTo(body3);

            var request1 = server.takeRequest();
            assertThat(request1.getPath()).isEqualTo("/v1/chat/messages/");
            assertThat(request1.getMethod()).isEqualTo("GET");

            var request2 = server.takeRequest();
            assertThat(request2.getPath()).isEqualTo("/v1/chat/messages/2");

            var request3 = server.takeRequest();
            assertThat(request3.getPath()).isEqualTo("/v1/chat/messages/3");
        }
    }
}
