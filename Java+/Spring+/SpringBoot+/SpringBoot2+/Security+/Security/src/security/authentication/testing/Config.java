package security.authentication.testing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
class Config {
    @Bean
    public TestingAuthenticationProvider testingAuthenticationProvider() {
        return new TestingAuthenticationProvider();
    }
}
