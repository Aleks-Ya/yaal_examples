package configuration.properties.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(PropertiesSettings.class)
@PropertySource("classpath:configuration/properties/properties/props.properties")
class PropertiesConfig {
}
