package ru;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({PropertiesSettings.class, YamlSettings.class})
class ApplicationConfiguration {
}
