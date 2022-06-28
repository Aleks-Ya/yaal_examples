package redisspringboot.embedded;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@TestConfiguration
//@SpringBootConfiguration
//@DataJpaTest
//@EnableAutoConfiguration
@ContextConfiguration(classes = {UserRepository.class})
@Import({RedisConfiguration.class, RedisProperties.class})
public class TestRedisConfiguration {

    private RedisServer redisServer;

    public TestRedisConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getRedisPort());
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}