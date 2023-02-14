package discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackageClasses = DiscoverClientApp.class)
public class DiscoverClientApp {
    public static void main(String[] args) {
        SpringApplication.run(DiscoverClientApp.class, args);
    }
}

@Component
class Exec {
    @Autowired
    private DiscoveryClient discoveryClient;

    @PostConstruct
    public void request() {
        var services = discoveryClient.getServices();
        assert services.size() == 2;
        System.out.println("Services: " + services);
    }
}
