package bean.name.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(name = "explicitName")
    public BeanWithExplicitName getBeanWithExplicitName() {
        return new BeanWithExplicitName();
    }

    /**
     * Сгенерируется имя: "generatedName".
     */
    @Bean
    public BeanWithGeneratedName generatedName() {
        return new BeanWithGeneratedName();
    }
}