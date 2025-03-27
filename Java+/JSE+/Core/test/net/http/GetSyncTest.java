package net.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class GetSyncTest {
    @Test
    void get() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body = "hello, world!";
            server.enqueue(new MockResponse().setBody(body));
            server.start();
            var baseUrl = server.url("/");

            var request = HttpRequest.newBuilder()
                    .uri(baseUrl.uri())
                    .GET()
                    .build();

            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                var statusCode = response.statusCode();
                assertThat(statusCode).isEqualTo(200);
            }
        }
    }

    @Test
    void getFile() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body = "hello, world!";
            var outputFilename = "message.txt";
            server.enqueue(new MockResponse()
                    .addHeader("Content-Disposition", format("attachment; filename=\"%s\"", outputFilename))
                    .setBody(body));
            server.start();
            var baseUrl = server.url("/");

            var outputDir = Files.createTempDirectory(GetSyncTest.class.getSimpleName());
            var request = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();
            var bodyHandler = HttpResponse.BodyHandlers.ofFileDownload(outputDir,
                    StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, bodyHandler);

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
    void get500() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var errorMessage = "No DB connection";
            var statusCode = 500;
            server.enqueue(new MockResponse().setResponseCode(statusCode).setBody(errorMessage));
            server.start();
            var baseUrl = server.url("/");

            var request = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();

            try (var client = HttpClient.newHttpClient()) {
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                assertThat(response.statusCode()).isEqualTo(statusCode);
                assertThat(response.body()).isEqualTo(errorMessage);
            }
        }
    }

}
