package security.pre_authorize.authenticated;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@Import(HelloMessageService.class)
@EnableMethodSecurity(securedEnabled = true)
class TestConfig {
}
