package validation.validated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Run:
 * 1. Start Application class
 * 2. Send valid request: curl -X POST http://localhost:8080/users -H 'Content-Type: application/json' -d'{"id": 1, "name": "John", "email": "john@mail.ru"}'
 * 3. Send invalid request: curl -X POST http://localhost:8080/users -H 'Content-Type: application/json' -d'{"id": 1, "name": "", "email": "john@mail.ru"}'
 * <p>
 * Source: https://www.baeldung.com/spring-boot-bean-validation
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
