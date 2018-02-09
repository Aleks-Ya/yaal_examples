package bean.inherit.component;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "bean.inherit.component",
        includeFilters = @ComponentScan.Filter(InheritedComponent.class))
class InheritComponentConfig {
}
