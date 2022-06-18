package property.source.custom;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:property/source/environment/custom.properties")
class CustomConfig {
}
