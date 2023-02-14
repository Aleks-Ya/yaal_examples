package feign.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Run WireMockServer with Spring and send HTTP request to it.
 */
@SpringBootTest
class WireMockTest {
    @Autowired
    private WireMockServer server;

    @Test
    void requestParam() throws Exception {
        server.start();
        var host = "localhost";
        var port = server.port();
        configureFor(host, port);
        var expBody = "Hello, World!";
        var path = "/hello";
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withBody(expBody)));

        var uri = URI.create(server.url(path));
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        var statusCode = response.statusCode();
        assertThat(statusCode).isEqualTo(200);
        var actBody = response.body();
        System.out.println(actBody);
        assertThat(actBody).isEqualTo(expBody);
    }

    @SpringBootConfiguration
    static class WireMockConfig {
        @Bean(initMethod = "start", destroyMethod = "stop")
        public WireMockServer wireMockServer() {
            return new WireMockServer(9562);
        }
    }
}