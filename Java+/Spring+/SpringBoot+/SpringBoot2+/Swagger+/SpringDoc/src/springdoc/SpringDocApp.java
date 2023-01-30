package springdoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * REST endpoint: http://localhost:8080/custom <br/>
 * Swagger UI: http://localhost:8080/swagger-ui/index.html
 */
@SpringBootApplication
public class SpringDocApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringDocApp.class, args);
    }
}
