package java9.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetSync {

    @Test
    public void get() throws IOException, InterruptedException {
        var server = new MockWebServer();
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

        server.shutdown();
    }

    @Test
    public void getFile() throws IOException, InterruptedException {
        var server = new MockWebServer();
        var body = "hello, world!";
        var outputFilename = "message.txt";
        server.enqueue(new MockResponse()
                .addHeader("Content-Disposition", format("attachment; filename=\"%s\"", outputFilename))
                .setBody(body));
        server.start();
        var baseUrl = server.url("/");

        var outputDir = Files.createTempDirectory(GetSync.class.getSimpleName());
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

        server.shutdown();
    }

}
