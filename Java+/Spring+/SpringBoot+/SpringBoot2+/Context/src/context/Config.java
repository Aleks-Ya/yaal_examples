package context;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class Config {
    @Bean
    public Person person() {
        return new Person("John", 30);
    }

    @Bean
    @ConfigurationProperties("information.city-info")
    public City city() {
        return new City();
    }

    @Bean
    public AllInfo allInfo(Person person, City city) {
        return new AllInfo(person, city);
    }

}
