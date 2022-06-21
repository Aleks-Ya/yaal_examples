package context.property.configuration_properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
class Config {
    @Bean
    Person person() {
        return new Person("John", 30);
    }

    @Bean
    @ConfigurationProperties("information.city-info")
    CityOnMethod city() {
        return new CityOnMethod();
    }

    @Bean
    AllInfo allInfo(Person person, CityOnMethod city) {
        return new AllInfo(person, city);
    }

}
