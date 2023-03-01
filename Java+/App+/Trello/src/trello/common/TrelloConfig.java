package trello.common;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

@Configuration
class TrelloConfig {
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    TrelloProperties trelloProperties() throws IOException {
        var propertiesPath = Paths.get(System.getProperty("user.home"), ".trello", "trello-api.properties");
        var propertiesFileReader = new FileReader(propertiesPath.toString());
        var properties = new Properties();
        properties.load(propertiesFileReader);
        return new TrelloProperties(
                properties.getProperty("baseUrl"),
                properties.getProperty("key"),
                properties.getProperty("token"),
                Arrays.asList(properties.getProperty("boardIds").split(",")));
    }
}
