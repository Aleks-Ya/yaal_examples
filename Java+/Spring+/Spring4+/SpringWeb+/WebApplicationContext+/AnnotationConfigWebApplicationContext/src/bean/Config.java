package bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public AnyRepository makeRepository() {
        return new AnyRepository();
    }

    @Bean
    public AnyService makeService() {
        return new AnyService();
    }
}