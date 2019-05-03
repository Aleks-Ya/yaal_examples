package property.source.environment;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:property/source/environment/environment.properties")
class EnvironmentConfig {
}
