package context.manual;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

class ManuallyStartSpringBootAppTest {
    @Test
    void run() {
        SpringApplication.run(App.class);
    }
}