package java9.http;

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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

            var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            var statusCode = response.statusCode();
            assertThat(statusCode, equalTo(200));
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
            var response = HttpClient.newHttpClient().send(request, bodyHandler);

            var statusCode = response.statusCode();
            assertThat(statusCode, equalTo(200));

            var outputFile = response.body();
            assertThat(outputFile, equalTo(outputDir.resolve(outputFilename)));

            var actContent = Files.readString(outputFile);
            assertThat(actContent, equalTo(body));
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

            var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode(), equalTo(statusCode));
            assertThat(response.body(), equalTo(errorMessage));
        }
    }

}
