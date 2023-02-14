package feign.httpbin.uuid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.annotation.PostConstruct;

/**
 * Send HTTP request to httpbin.org with Feign (from standalone app).
 */
@SpringBootApplication(scanBasePackageClasses = UuidApp.class)
@EnableFeignClients
public class UuidApp {
    public static void main(String[] args) {
        SpringApplication.run(UuidApp.class, args);
    }
}

@FeignClient(name = "http-bin-client", url = "https://httpbin.org")
interface HttpBinApi {
    @RequestMapping(method = RequestMethod.GET, value = "/uuid")
    String uuid();
}

@Component
class Exec {
    @Autowired
    HttpBinApi httpBinApi;

    @PostConstruct
    public void request() {
        var json = httpBinApi.uuid();
        assert !json.isEmpty();
        System.out.println("JSON: " + json);
    }
}
