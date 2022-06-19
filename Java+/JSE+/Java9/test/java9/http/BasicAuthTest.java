package java9.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class BasicAuthTest {
    private static final String USERNAME = "the_user";
    private static final String PASSWORD = "the_pass";
    private static final URI ENDPOINT = URI.create(format("https://httpbin.org/basic-auth/%s/%s", USERNAME, PASSWORD));

    @Test
    void successAuthenticator() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(ENDPOINT).GET().build();
        var client = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD.toCharArray());
                    }
                })
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var statusCode = response.statusCode();
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    void successHttpHeader() throws IOException, InterruptedException {
        var valueToEncode = USERNAME + ":" + PASSWORD;
        var headerValue = "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
        var request = HttpRequest.newBuilder()
                .header("Authorization", headerValue)
                .uri(ENDPOINT).GET().build();
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        var statusCode = response.statusCode();
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    void failAnonymous() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder().uri(ENDPOINT).GET().build();
        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        var statusCode = response.statusCode();
        assertThat(statusCode).isEqualTo(401);
    }

}
