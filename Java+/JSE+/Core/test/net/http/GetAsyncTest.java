package net.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.assertj.core.api.Assertions.assertThat;

class GetAsyncTest {

    @Test
    void get() throws IOException {
        try (var server = new MockWebServer()) {
            var body1 = "hello, world!";
            var body2 = "buy, world!";
            server.enqueue(new MockResponse().setBody(body1));
            server.enqueue(new MockResponse().setBody(body2));
            server.start();
            var baseUrl = server.url("/");

            var request1 = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();
            var request2 = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();

            try (var client = HttpClient.newHttpClient()) {
                var responseFuture1 = client.sendAsync(request1, ofString());
                var responseFuture2 = client.sendAsync(request2, ofString());
                CompletableFuture.allOf(responseFuture1, responseFuture2).join();

                var response1 = responseFuture1.join();
                assertThat(response1.statusCode()).isEqualTo(200);
                assertThat(response1.body()).isEqualTo(body1);

                var response2 = responseFuture2.join();
                assertThat(response2.statusCode()).isEqualTo(200);
                assertThat(response2.body()).isEqualTo(body2);
            }
        }
    }

    @Test
    void getFile() throws IOException {
        try (var server = new MockWebServer()) {
            var body = "hello, world!";
            var outputFilename = "message.txt";
            server.enqueue(new MockResponse()
                    .addHeader("Content-Disposition", format("attachment; filename=\"%s\"", outputFilename))
                    .setBody(body));
            server.start();
            var baseUrl = server.url("/");

            var outputDir = Files.createTempDirectory(GetAsyncTest.class.getSimpleName());
            var request = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();
            var bodyHandler = HttpResponse.BodyHandlers.ofFileDownload(outputDir,
                    StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            try (var client = HttpClient.newHttpClient()) {
                var responseFuture = client.sendAsync(request, bodyHandler);
                var response = responseFuture.join();
                var statusCode = response.statusCode();
                assertThat(statusCode).isEqualTo(200);

                var outputFile = response.body();
                assertThat(outputFile).isEqualTo(outputDir.resolve(outputFilename));

                var actContent = Files.readString(outputFile);
                assertThat(actContent).isEqualTo(body);
            }
        }
    }

    @Test
    void getThenProcessBody() throws IOException {
        try (var server = new MockWebServer()) {
            var body = "Hello, World!";
            server.enqueue(new MockResponse().setBody(body));
            server.enqueue(new MockResponse().setBody(body));
            server.start();
            var baseUrl = server.url("/");

            var request1 = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();
            var request2 = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();

            try (var client = HttpClient.newHttpClient()) {
                var responseFuture1 = client.sendAsync(request1, ofString())
                        .thenCompose(response -> CompletableFuture.supplyAsync(() -> response.body().toUpperCase()));
                var responseFuture2 = client.sendAsync(request2, ofString())
                        .thenCompose(response -> CompletableFuture.supplyAsync(() -> response.body().toLowerCase()));
                CompletableFuture.allOf(responseFuture1, responseFuture2).join();

                var actBody1 = responseFuture1.join();
                assertThat(actBody1).isEqualTo(body.toUpperCase());

                var actBody2 = responseFuture2.join();
                assertThat(actBody2).isEqualTo(body.toLowerCase());
            }
        }
    }

    @Test
    @Disabled("Not finished")
    void cancel() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body = "hello, world!";
            server.enqueue(new MockResponse().setBody(body).setBodyDelay(1, TimeUnit.MINUTES));
            server.start();
            var baseUrl = server.url("/");

            var request = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();

            try (var client = HttpClient.newHttpClient()) {
                var responseFuture = client.sendAsync(request, ofString());
                Thread.sleep(1000);
                var result = responseFuture.cancel(false);
                System.out.println("Result: " + result);

                var response = responseFuture.join();
                assertThat(response.statusCode()).isEqualTo(200);
                assertThat(response.body()).isEqualTo(body);

                var recordedRequest = server.takeRequest();
                assertThat(recordedRequest.getBody().readString(Charset.defaultCharset())).isEqualTo(body);
            }
        }
    }

}
