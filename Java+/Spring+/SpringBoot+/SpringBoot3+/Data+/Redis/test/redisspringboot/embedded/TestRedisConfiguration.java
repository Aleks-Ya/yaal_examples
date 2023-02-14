package redisspringboot.embedded;

import org.springframework.context.annotation.Import;
import redis.embedded.RedisServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Import({RedisConfiguration.class, RedisProperties.class})
class TestRedisConfiguration {

    private final RedisServer redisServer;

    TestRedisConfiguration(RedisProperties redisProperties) {
        redisServer = new RedisServer(redisProperties.getRedisPort());
    }

    @PostConstruct
    void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    void preDestroy() {
        redisServer.stop();
    }
}