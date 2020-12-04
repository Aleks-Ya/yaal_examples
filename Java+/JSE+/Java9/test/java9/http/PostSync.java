package java9.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostSync {

    @Test
    public void post() throws IOException, InterruptedException {
        var server = new MockWebServer();
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
        assertThat(response.statusCode(), equalTo(200));

        var recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath(), equalTo(path));
        assertThat(recordedRequest.getMethod(), equalTo("POST"));
        assertThat(recordedRequest.getBody().readString(Charset.defaultCharset()), equalTo(body));

        server.shutdown();
    }

    @Test
    public void postMultipart() throws IOException, InterruptedException {
        var server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.start();
        var path = "/";
        var baseUrl = server.url(path);

        var bodyPublisher = ofFormData(Map.of(
                "message1", "abc",
                "message2", "efg"));
        var request = HttpRequest.newBuilder()
                .uri(baseUrl.uri())
                .POST(bodyPublisher)
                .header("Content-Type", "multipart/form-data")
                .build();

        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(200));

        var recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath(), equalTo(path));
        assertThat(recordedRequest.getMethod(), equalTo("POST"));
        assertThat(recordedRequest.getBody().readString(Charset.defaultCharset()),
                equalTo("message1=abc&message2=efg"));

        server.shutdown();
    }

    private static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        return HttpRequest.BodyPublishers.ofString(
                new TreeMap<>(data).entrySet().stream()
                        .map(entry -> URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8) + "=" +
                                URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8))
                        .collect(Collectors.joining("&"))
        );
    }
}
