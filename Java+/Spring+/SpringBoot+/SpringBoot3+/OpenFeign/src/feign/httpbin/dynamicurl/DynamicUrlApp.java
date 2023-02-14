package feign.httpbin.dynamicurl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.annotation.PostConstruct;
import java.net.URI;

import static feign.httpbin.dynamicurl.DynamicUrlApp.SERVICE_ID;

/**
 * Send HTTP request to httpbin.org with Feign (from standalone app).
 * Specify target URL at runtime.
 */
@SpringBootApplication(scanBasePackageClasses = DynamicUrlApp.class)
@EnableFeignClients
public class DynamicUrlApp {
    static final String SERVICE_ID = "httpbin";

    public static void main(String[] args) {
        new SpringApplicationBuilder(DynamicUrlApp.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}

@FeignClient(name = SERVICE_ID, url = "http://fake.com")
interface HttpBinApi {
    @RequestMapping(method = RequestMethod.GET, path = "/uuid")
    String uuid(URI baseUri);
}

@Component
class Exec {
    @Autowired
    HttpBinApi httpBinApi;

    @PostConstruct
    public void request() {
        var baseUri = URI.create("https://httpbin.org");
        var json = httpBinApi.uuid(baseUri);
        assert !json.isEmpty();
        System.out.println("JSON: " + json);
    }
}
