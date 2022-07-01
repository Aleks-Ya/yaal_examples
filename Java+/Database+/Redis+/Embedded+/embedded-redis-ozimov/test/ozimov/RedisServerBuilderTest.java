package ozimov;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.config.Config;
import redis.embedded.RedisServer;

import static org.assertj.core.api.Assertions.assertThat;

class RedisServerBuilderTest {

    @Test
    void ping() {
        var port = 6377;
        var redisServer = RedisServer.builder().port(port).build();
        redisServer.start();

        var config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:" + port);
        var redisson = Redisson.create(config);
        var success = redisson.getRedisNodes(RedisNodes.SINGLE).pingAll();
        assertThat(success).isTrue();
        redisson.shutdown();

        redisServer.stop();
    }

    @Test
    void setting() {
        var port = 6377;
        var redisServer = RedisServer.builder()
                .port(port)
                .setting("requirepass pass1")
                .build();
        redisServer.start();

        var config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:" + port).setPassword("pass1");
        var redisson = Redisson.create(config);
        var success = redisson.getRedisNodes(RedisNodes.SINGLE).pingAll();
        assertThat(success).isTrue();
        redisson.shutdown();

        redisServer.stop();
    }
}