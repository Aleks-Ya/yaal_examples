package multi;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:multi/multi_1.properties"),
        @PropertySource("classpath:multi/multi_2.properties")
})
class MultiConfig {
}
