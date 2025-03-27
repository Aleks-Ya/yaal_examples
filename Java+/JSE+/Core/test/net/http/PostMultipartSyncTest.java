package net.http;

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
import static org.assertj.core.api.Assertions.assertThat;

class PostMultipartSyncTest {

    private static HttpRequest.BodyPublisher ofMultipartData(Map<Object, Object> data, String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        var separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(UTF_8);
        for (var entry : data.entrySet()) {
            byteArrays.add(separator);
            if (entry.getValue() instanceof Path path) {
                var mimeType = Files.probeContentType(path);
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
    void postMultipartManually() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse());
            server.start();
            var path = "/";
            var baseUrl = server.url(path);

            var boundary = "-------------" + UUID.randomUUID();
            var request = HttpRequest.newBuilder()
                    .uri(baseUrl.uri())
                    .POST(ofMultipartData(Map.of(
                            "message1", "abc",
                            "message2", "efg"
                    ), boundary))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .build();

            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                assertThat(response.statusCode()).isEqualTo(200);

                var recordedRequest = server.takeRequest();
                assertThat(recordedRequest.getPath()).isEqualTo(path);
                assertThat(recordedRequest.getMethod()).isEqualTo("POST");
                assertThat(recordedRequest.getBody().readString(Charset.defaultCharset()))
                        .containsSubsequence(boundary)
                        .containsSubsequence("Content-Disposition: form-data; name=\"message1\"\r\n\r\nabc\r\n")
                        .containsSubsequence("Content-Disposition: form-data; name=\"message2\"\r\n\r\nefg\r\n");
            }
        }
    }

    @Test
    void postMultipartWithLibrary() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse());
            server.start();
            var path = "/";
            var baseUrl = server.url(path);

            var boundary = "-------------" + UUID.randomUUID();
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

            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                assertThat(response.statusCode()).isEqualTo(200);

                var recordedRequest = server.takeRequest();
                assertThat(recordedRequest.getPath()).isEqualTo(path);
                assertThat(recordedRequest.getMethod()).isEqualTo("POST");
                assertThat(recordedRequest.getBody().readString(Charset.defaultCharset()))
                        .containsSubsequence(boundary)
                        .containsSubsequence("Content-Disposition: form-data; name=\"message1\"\r\n\r\nabc\r\n")
                        .containsSubsequence("Content-Disposition: form-data; name=\"message2\"\r\n\r\nefg\r\n");
            }
        }
    }
}
