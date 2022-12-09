package flux;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class WebClientTest {

    @Test
    void get() throws IOException {
        try (var server = new MockWebServer()) {
            var expBody = "abc";
            server.enqueue(new MockResponse().setBody(expBody));
            server.start();

            var uri = server.url("/").uri();

            var client = WebClient.create();

            var mono = client.get().uri(uri).retrieve();
            var actBody = mono.bodyToMono(String.class).block();
            assertThat(actBody).isEqualTo(expBody);
        }
    }
}