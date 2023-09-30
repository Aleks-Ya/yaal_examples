package server.dispatcher;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Return different responses depending on request body.
 */
class BodyDispatcherTest {

    @Test
    void bodyDispatcher() throws InterruptedException, IOException {
        try (var server = new MockWebServer()) {
            var path = "/person";
            var responseBody1 = "John";
            var responseBody2 = "BMW";
            var responseBodyUnknown = "Unknown response body";
            final var requestBody1 = "Request Body 1";
            final var requestBody2 = "Request Body 2";

            server.setDispatcher(new Dispatcher() {
                @NotNull
                @Override
                public MockResponse dispatch(@NotNull RecordedRequest request) {
                    return switch (request.getBody().readUtf8()) {
                        case requestBody1:
                            yield new MockResponse().setResponseCode(200).setBody(responseBody1);
                        case requestBody2:
                            yield new MockResponse().setResponseCode(200).setBody(responseBody2);
                        default:
                            yield new MockResponse().setResponseCode(400).setBody(responseBodyUnknown);
                    };
                }
            });

            var baseUrl = server.url(path);

            var request1 = HttpRequest.newBuilder().uri(baseUrl.uri()).POST(ofString(requestBody1)).build();
            var response1 = HttpClient.newHttpClient().send(request1, HttpResponse.BodyHandlers.ofString());
            assertThat(response1.statusCode()).isEqualTo(200);
            assertThat(response1.body()).isEqualTo(responseBody1);

            var request2 = HttpRequest.newBuilder().uri(baseUrl.uri()).POST(ofString(requestBody2)).build();
            var response2 = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());
            assertThat(response2.statusCode()).isEqualTo(200);
            assertThat(response2.body()).isEqualTo(responseBody2);

            var requestUnknown = HttpRequest.newBuilder().uri(baseUrl.uri()).POST(ofString("unknown")).build();
            var responseUnknown = HttpClient.newHttpClient().send(requestUnknown, HttpResponse.BodyHandlers.ofString());
            assertThat(responseUnknown.statusCode()).isEqualTo(400);
            assertThat(responseUnknown.body()).isEqualTo(responseBodyUnknown);
        }
    }

}
