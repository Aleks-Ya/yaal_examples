package property.source.value;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:property/source/value/value.properties")
class ValueConfig {
}
