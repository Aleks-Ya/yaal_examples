package configuration.properties.yaml;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableConfigurationProperties(YamlSettings.class)
class YamlConfig {

    /**
     * {@link org.springframework.context.annotation.PropertySource} doesn't support YAML files (only *.properties).
     * This method requeres 'org.yaml:snakeyaml:1.18' dependency.
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer yaml() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("configuration/properties/yaml/props.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }
}
