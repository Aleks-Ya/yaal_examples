package feign.person;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Use Feign to send HTTP request to WireMockServer (in unit-test).
 */
@SpringBootTest(webEnvironment = NONE)
class FeignTest {
    @Autowired
    private WireMockServer server;

    @Autowired
    PersonApi personApi;

    @Test
    void requestParam() {
        server.start();
        var host = "localhost";
        var port = server.port();
        configureFor(host, port);
        var expBody = "Hello, World!";
        var path = "/hello";
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withBody(expBody)));

        var actBody = personApi.getFirstName();

        System.out.println(actBody);
        assertThat(actBody).isEqualTo(expBody);
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableFeignClients(clients = PersonApi.class)
    static class WireMockConfig {
        @Bean(initMethod = "start", destroyMethod = "stop")
        public WireMockServer wireMockServer() {
            return new WireMockServer(9561);
        }
    }

    @FeignClient(value = "person", url = "http://localhost:9561")
    public interface PersonApi {
        @RequestMapping(method = RequestMethod.GET, value = "/hello")
        String getFirstName();
    }
}