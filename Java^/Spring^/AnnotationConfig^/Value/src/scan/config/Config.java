package scan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class Config {
    @Bean
    public DateFormat dateFormat() {
        return SimpleDateFormat.getDateInstance();
    }
}
