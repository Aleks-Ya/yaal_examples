package jedis.local;

import jedis.Factory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prepare: run Redis Server (see "CLI/Redis/RedisServer.md").
 */
class LocalRedisServerTest {
    private static final int PORT = 6377;
    private static final String PASSWORD = "pass1";

    @Test
    void ping() {
        try (var pool = Factory.newPool(PORT, PASSWORD);
             var jedis = pool.getResource()) {
            var response = jedis.ping();
            assertThat(response).isEqualTo("PONG");
        }
    }

    @Test
    void setKey() {
        try (var pool = Factory.newPool(PORT, PASSWORD);
             var jedis = pool.getResource()) {
            var expValue = "Jedis";
            var key = "clientName";
            jedis.set(key, expValue);
            var actValue = jedis.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}