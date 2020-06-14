package flux;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThrows;

public class ErrorCodeTest {

    @Test
    public void error() throws IOException {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(418));
        server.start();

        WebClient client = WebClient.create();

        URI uri = server.url("/").uri();
        WebClient.ResponseSpec mono = client.get().uri(uri).retrieve();
        WebClientResponseException e = assertThrows(WebClientResponseException.class,
                () -> mono.bodyToMono(String.class).block());
        assertThat(e.getMessage(), startsWith("418 I'm a teapot from GET"));

        server.shutdown();
    }
}