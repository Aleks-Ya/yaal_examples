package usual;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
        @PropertySource("classpath:usual/my.properties"),
        /**
         * Можно задать путь к файлу свойств через свойства JVM: -Dconf=file:${user.dir}/resources/city2.properties
         * Если свойство "conf" не задано, будет использовано значение по-умолчанию.
         */
        @PropertySource("${conf:file:${user.dir}/resources/usual/city.properties}")
})
public class SpringConfig {

    /**
     * To resolve ${} in @Value
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
