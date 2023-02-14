package redisspringboot.embedded;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedisProperties {
    private final int redisPort;
    private final String redisHost;

    RedisProperties(@Value("${spring.redis.host}") String redisHost,
                    @Value("${spring.redis.port}") int redisPort) {
        this.redisPort = redisPort;
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public String getRedisHost() {
        return redisHost;
    }
}
