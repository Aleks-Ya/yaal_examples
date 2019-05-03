package property.source.multi;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:property/source/multi/multi_1.properties"),
        @PropertySource("classpath:property/source/multi/multi_2.properties")
})
class MultiConfig {
}
