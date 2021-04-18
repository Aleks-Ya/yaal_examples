package java9.http;

import com.github.mizosoft.methanol.MultipartBodyPublisher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PostMultipartSync {

    @Test
    public void postMultipartManually() throws IOException, InterruptedException {
        var server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.start();
        var path = "/";
        var baseUrl = server.url(path);

        String boundary = "-------------" + UUID.randomUUID().toString();
        var request = HttpRequest.newBuilder()
                .uri(baseUrl.uri())
                .POST(ofMultipartData(Map.of(
                        "message1", "abc",
                        "message2", "efg"
                ), boundary))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .build();

        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(200));

        var recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath(), equalTo(path));
        assertThat(recordedRequest.getMethod(), equalTo("POST"));
        assertThat(recordedRequest.getBody().readString(Charset.defaultCharset()), allOf(
                containsString(boundary),
                containsString("Content-Disposition: form-data; name=\"message1\"\r\n\r\nabc\r\n"),
                containsString("Content-Disposition: form-data; name=\"message2\"\r\n\r\nefg\r\n")));

        server.shutdown();
    }

    public static HttpRequest.BodyPublisher ofMultipartData(Map<Object, Object> data, String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(UTF_8);
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            byteArrays.add(separator);
            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName() +
                        "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n").getBytes(UTF_8));
            }
        }
        byteArrays.add(("--" + boundary + "--").getBytes(UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }

    @Test
    public void postMultipartWithLibrary() throws IOException, InterruptedException {
        var server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.start();
        var path = "/";
        var baseUrl = server.url(path);

        String boundary = "-------------" + UUID.randomUUID().toString();
        var bodyPublisher = MultipartBodyPublisher.newBuilder()
                .boundary(boundary)
                .textPart("message1", "abc")
                .textPart("message2", "efg")
                .build();
        var request = HttpRequest.newBuilder()
                .uri(baseUrl.uri())
                .POST(bodyPublisher)
                .header("Content-Type", "multipart/form-data boundary=" + boundary)
                .build();

        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(200));

        var recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath(), equalTo(path));
        assertThat(recordedRequest.getMethod(), equalTo("POST"));
        assertThat(recordedRequest.getBody().readString(Charset.defaultCharset()), allOf(
                containsString(boundary),
                containsString("Content-Disposition: form-data; name=\"message1\"\r\n\r\nabc\r\n"),
                containsString("Content-Disposition: form-data; name=\"message2\"\r\n\r\nefg\r\n")));

        server.shutdown();
    }
}
