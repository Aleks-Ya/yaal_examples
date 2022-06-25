package jedis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPool;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prepare: run Redis Server (see "CLI/Redis/RedisServer.md").
 */
class LocalRedisServerTest {

    @Test
    void connect() {
        try (var pool = new JedisPool("localhost", 6379);
             var jedis = pool.getResource()) {
            var expValue = "Jedis";
            var key = "clientName";
            jedis.set(key, expValue);
            var actValue = jedis.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}