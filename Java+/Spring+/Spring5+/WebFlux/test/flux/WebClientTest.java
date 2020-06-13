package flux;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WebClientTest {

    @Test
    public void get() throws IOException {
        MockWebServer server = new MockWebServer();
        String expBody = "abc";
        server.enqueue(new MockResponse().setBody(expBody));
        server.start();

        URI uri = server.url("/").uri();

        WebClient client1 = WebClient.create();

        WebClient.ResponseSpec mono = client1.get().uri(uri).retrieve();
        String actBody = mono.bodyToMono(String.class).block();
        assertThat(actBody, equalTo(expBody));

        server.shutdown();
    }
}