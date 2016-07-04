package spring;

import common.MongoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author Yablokov Aleksey
 */
@Configuration
class MongoConfig {
    private MongoFactory factory;

    @PostConstruct
    private void initFactory() throws IOException {
        factory = new MongoFactory();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(factory.getMongoClient(), factory.getDatabaseName());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
