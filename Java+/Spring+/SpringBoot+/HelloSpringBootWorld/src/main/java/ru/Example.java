package ru;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@EnableConfigurationProperties
@Import({PropertiesSettings.class, YamlSettings.class})
public class Example {

    @Autowired
    private PropertiesSettings propertiesSettings;
    @Autowired
    private YamlSettings yamlSettings;

    /**
     * http://localhost:8080/
     */
    @RequestMapping("/")
    String home() {
        return propertiesSettings.getMessage() + " " + propertiesSettings.getSystem().getStatus() + " " + yamlSettings.getSuffix();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }
}