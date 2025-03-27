package net.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class LoggerTest {
    @Test
    void logLevel() throws IOException, InterruptedException {
        System.setProperty("jdk.httpclient.HttpClient.log", "ALL");
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setBody("hello, world!"));
            server.start();
            var baseUrl = server.url("/");
            var request = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();
            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                assertThat(response.statusCode()).isEqualTo(200);
            }
        }
    }
}
