package java9.http;

import com.github.mizosoft.methanol.MultipartBodyPublisher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
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

        var bodyPublisher = MultipartBodyPublisher.newBuilder()
                .textPart("message1", "abc")
                .textPart("message2", "efg")
                .build();
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
        assertThat(recordedRequest.getBody().readString(Charset.defaultCharset()), allOf(
                containsString("Content-Disposition: form-data; name=\"message1\"\r\n\r\nabc\r\n"),
                containsString("Content-Disposition: form-data; name=\"message2\"\r\n\r\nefg\r\n")));

        server.shutdown();
    }
}
