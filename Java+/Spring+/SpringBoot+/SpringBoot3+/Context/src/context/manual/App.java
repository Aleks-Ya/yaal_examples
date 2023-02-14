package context.manual;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App {
    @PostConstruct
    private void hello() {
        System.out.println("Hello, World!");
    }
}
