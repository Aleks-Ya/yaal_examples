package spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Yablokov Aleksey
 */
@Configuration
@ComponentScan(basePackages = {"spring"})
@EnableMongoRepositories(basePackages = "repository")
public class TestConfiguration {

}


