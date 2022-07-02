package app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("file:/config/game.properties"),
        @PropertySource("file:/config/ui.properties")
})
class Config {
}
