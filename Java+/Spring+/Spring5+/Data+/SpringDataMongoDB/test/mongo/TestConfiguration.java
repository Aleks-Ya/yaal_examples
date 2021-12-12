package mongo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {"mongo"})
@EnableMongoRepositories(basePackages = "mongo")
public class TestConfiguration {

}


