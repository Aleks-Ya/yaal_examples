package wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;

class VerifyHeaderTest {

    @Test
    void header() throws IOException, InterruptedException {
        var server = new WireMockServer();
        server.start();

        var host = "localhost";
        var port = server.port();
        configureFor(host, port);
        var expBody = "Hello, World!";
        var path = "/hello";
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withBody(expBody)));

        var url = URI.create(server.url(path));
        var headerName = "Authorization";
        var headerValue = "Basic abc";
        var request = HttpRequest.newBuilder()
                .uri(url)
                .header(headerName, headerValue)
                .GET()
                .build();

        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        var actBody = response.body();

        verify(getRequestedFor(urlEqualTo(path)).withHeader(headerName, equalTo(headerValue)));
        assertThat(expBody).isEqualTo(actBody);

        server.stop();
    }
}
