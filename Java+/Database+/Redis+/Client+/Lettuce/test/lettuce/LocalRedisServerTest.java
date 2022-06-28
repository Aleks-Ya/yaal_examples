package lettuce;

import io.lettuce.core.api.sync.RedisStringCommands;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prepare: run Redis Server (see "CLI/Redis/RedisServer.md").
 */
class LocalRedisServerTest {

    @Test
    void ping() {
        try (var connection = Factory.newConnection()) {
            var response = connection.sync().ping();
            assertThat(response).isEqualTo("PONG");
        }
    }

    @Test
    void setKey() {
        try (var connection = Factory.newConnection()) {
            RedisStringCommands<String, String> sync = connection.sync();
            var key = "key-" + LocalRedisServerTest.class.getSimpleName();
            var expValue = "value1";
            sync.set(key, expValue);
            var actValue = sync.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}