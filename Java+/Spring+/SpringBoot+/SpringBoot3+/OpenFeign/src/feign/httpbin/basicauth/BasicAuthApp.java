package feign.httpbin.basicauth;

import feign.auth.BasicAuthRequestInterceptor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Send HTTP request to httpbin.org with Feign with Basic Authentication (from standalone app).
 * The same credentials for all requests.
 */
@SpringBootApplication(scanBasePackageClasses = BasicAuthApp.class)
public class BasicAuthApp {
    public static void main(String[] args) {
        SpringApplication.run(BasicAuthApp.class, args);
    }
}

@FeignClient(name = "http-bin-client", url = "https://httpbin.org")
interface HttpBinApi {
    @RequestMapping(method = GET, value = "/basic-auth/the_user/the_pass")
    String basicAuth();
}

@Configuration
@EnableFeignClients
class FeignConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("the_user", "the_pass");
    }
}

@Component
class Exec {
    @Autowired
    HttpBinApi httpBinApi;

    @PostConstruct
    public void request() {
        var json = httpBinApi.basicAuth();
        assert json.contains("\"authenticated\": true");
        assert json.contains("\"user\": \"the_user\"");
        System.out.println("JSON: " + json);
    }
}
