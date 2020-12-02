package java9.http;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Http {

    @Test
    public void get() throws IOException, InterruptedException {
        MockWebServer server = new MockWebServer();
        String body = "hello, world!";
        server.enqueue(new MockResponse().setBody(body));
        server.start();
        HttpUrl baseUrl = server.url("/");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(baseUrl.uri())
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        assertThat(statusCode, equalTo(200));

        server.shutdown();
    }

    @Test
    public void getFile() throws IOException, InterruptedException {
        MockWebServer server = new MockWebServer();
        String body = "hello, world!";
        String outputFilename = "message.txt";
        server.enqueue(new MockResponse()
                .addHeader("Content-Disposition", format("attachment; filename=\"%s\"", outputFilename))
                .setBody(body));
        server.start();
        HttpUrl baseUrl = server.url("/");

        Path outputDir = Files.createTempDirectory(Http.class.getSimpleName());
        HttpRequest request = HttpRequest.newBuilder().uri(baseUrl.uri()).GET().build();
        HttpResponse.BodyHandler<Path> bodyHandler = HttpResponse.BodyHandlers.ofFileDownload(outputDir,
                StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        HttpResponse<Path> response = HttpClient.newHttpClient().send(request, bodyHandler);

        int statusCode = response.statusCode();
        assertThat(statusCode, equalTo(200));

        Path outputFile = response.body();
        assertThat(outputFile, equalTo(outputDir.resolve(outputFilename)));

        String actContent = Files.readString(outputFile);
        assertThat(actContent, equalTo(body));

        server.shutdown();
    }

}
