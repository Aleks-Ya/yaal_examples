package azure.azurite;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@PropertySource("file:/home/aleks/.azure-examples/spring-azure.properties")
class BlobServiceConfig {
}
