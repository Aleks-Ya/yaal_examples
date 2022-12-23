package jpa.eclipselink.test_hierarchy.no_base_class.manytests2.skip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShouldNotBeUsedConfig {
    @Bean
    String fail() {
        throw new IllegalStateException();
    }
}
