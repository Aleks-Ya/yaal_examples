package environment;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:environment/environment.properties")
class EnvironmentConfig {
}
