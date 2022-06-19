package java9.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

class PostSyncTest {

    @Test
    void post() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body = "hello, world!";
            server.enqueue(new MockResponse());
            server.start();
            var path = "/";
            var baseUrl = server.url(path);

            var request = HttpRequest.newBuilder()
                    .uri(baseUrl.uri())
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).isEqualTo(200);

            var recordedRequest = server.takeRequest();
            assertThat(recordedRequest.getPath()).isEqualTo(path);
            assertThat(recordedRequest.getMethod()).isEqualTo("POST");
            assertThat(recordedRequest.getBody().readString(Charset.defaultCharset())).isEqualTo(body);
        }
    }
}
