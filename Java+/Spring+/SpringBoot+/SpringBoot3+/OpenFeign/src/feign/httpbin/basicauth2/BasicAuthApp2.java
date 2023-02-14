package feign.httpbin.basicauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Send HTTP request to httpbin.org with Feign with Basic Authentication (from standalone app).
 * Unique credentials for each requests.
 */
@SpringBootApplication(scanBasePackageClasses = BasicAuthApp2.class)
@EnableFeignClients
public class BasicAuthApp2 {
    public static void main(String[] args) {
        SpringApplication.run(BasicAuthApp2.class, args);
    }
}

@FeignClient(name = "http-bin-client", url = "https://httpbin.org")
interface HttpBinApi {
    @RequestMapping(method = GET, value = "/basic-auth/the_user/the_pass")
    String basicAuth(@RequestHeader("Authorization") String basicHeader);
}

@Component
class Exec {
    @Autowired
    HttpBinApi httpBinApi;

    @PostConstruct
    public void request() {
        var basicHeader = "Basic " + Base64.getEncoder().encodeToString("the_user:the_pass".getBytes(StandardCharsets.UTF_8));
        var json = httpBinApi.basicAuth(basicHeader);
        assert json.contains("\"authenticated\": true");
        assert json.contains("\"user\": \"the_user\"");
        System.out.println("JSON: " + json);
    }
}
