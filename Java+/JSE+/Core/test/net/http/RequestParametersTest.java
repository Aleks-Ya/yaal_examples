package net.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

class RequestParametersTest {
    @Test
    void params() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body = "hello, world!";
            server.enqueue(new MockResponse().setBody(body));
            server.start();
            var baseUrl = server.url("/");
            var paramMap = Map.of("param1", "value1",
                    "param2", "value2");
            var paramStr = paramMap.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(joining("&"));
            var url = URI.create(baseUrl + "?" + paramStr);

            var request = HttpRequest.newBuilder().uri(url).GET().build();
            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                var statusCode = response.statusCode();
                assertThat(statusCode).isEqualTo(200);

                var actRequest = server.takeRequest();
                assertThat(actRequest.getPath()).contains("param1=value1").contains("param2=value2");
            }
        }
    }
}
