package context.manual;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class App {
    @PostConstruct
    private void hello(){
        System.out.println("Hello, World!");
    }
}
