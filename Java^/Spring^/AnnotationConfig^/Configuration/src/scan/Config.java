package scan;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    /**
     * Простой singleton-бин.
     */
    @Bean
    public Book makeBook() {
        return new Book(1, "Набоков");
    }
}