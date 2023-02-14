package feign.httpbin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Send HTTP request to httpbin.org with Feign (from unit-test).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class HttpBinTest {
    @Autowired
    HttpBinApi httpBinApi;

    @Test
    void requestParam() {
        var json = httpBinApi.uuid();
        System.out.println("JSON: " + json);
        assertThat(json).isNotEmpty();
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableFeignClients(clients = HttpBinTest.HttpBinApi.class)
    static class WireMockConfig {
    }

    @FeignClient(name = "http-bin-client", url = "https://httpbin.org")
    interface HttpBinApi {
        @RequestMapping(method = RequestMethod.GET, value = "/uuid")
        String uuid();
    }
}