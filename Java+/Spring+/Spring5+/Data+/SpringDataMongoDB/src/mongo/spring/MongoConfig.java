package mongo.spring;

import common.MongoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
class MongoConfig {
    private MongoFactory factory;

    @PostConstruct
    private void initFactory() throws IOException {
        factory = new MongoFactory();
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(factory.getMongoClient2(), factory.getDatabaseName());
    }
}
