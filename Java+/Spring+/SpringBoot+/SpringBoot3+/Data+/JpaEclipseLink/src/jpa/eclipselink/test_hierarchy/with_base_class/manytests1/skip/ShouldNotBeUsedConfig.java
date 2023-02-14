package jpa.eclipselink.test_hierarchy.with_base_class.manytests1.skip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShouldNotBeUsedConfig {
    @Bean
    String fail() {
        throw new IllegalStateException();
    }
}
