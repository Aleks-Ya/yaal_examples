package ozimov;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.config.Config;
import redis.embedded.RedisServer;

import static org.assertj.core.api.Assertions.assertThat;

class RedisServerTest {

    @Test
    void ping() {
        var port = 6377;
        var redisServer = new RedisServer(port);
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
    void setKey() {
        var port = 6377;
        var redisServer = new RedisServer(port);
        redisServer.start();

        var config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:" + port);
        var redisson = Redisson.create(config);
        var long1 = redisson.getAtomicLong("long1");
        var expValue = 1L;
        long1.set(expValue);
        var actValue = long1.get();
        assertThat(actValue).isEqualTo(expValue);
        redisson.shutdown();

        redisServer.stop();
    }
}