package flux;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ErrorCodeTest {

    @Test
    void error() throws IOException {
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setResponseCode(418));
            server.start();

            var client = WebClient.create();

            var uri = server.url("/").uri();
            var mono = client.get().uri(uri).retrieve();
            var e = assertThrows(WebClientResponseException.class,
                    () -> mono.bodyToMono(String.class).block());
            assertThat(e.getMessage()).startsWith("418 I'm a teapot from GET");
        }
    }
}