package lettuce.local;

import io.lettuce.core.api.sync.RedisStringCommands;
import lettuce.Factory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prepare: run Redis Server (see "CLI/Redis/RedisServer.md").
 */
class LocalRedisServerTest {
    private static final int PORT = 6379;
    private static final String PASSWORD = "pass1";

    @Test
    void ping() {
        try (var connection = Factory.newConnection(PORT, PASSWORD)) {
            var response = connection.sync().ping();
            assertThat(response).isEqualTo("PONG");
        }
    }

    @Test
    void setKey() {
        try (var connection = Factory.newConnection(PORT, PASSWORD)) {
            RedisStringCommands<String, String> sync = connection.sync();
            var key = "key-" + LocalRedisServerTest.class.getSimpleName();
            var expValue = "value1";
            sync.set(key, expValue);
            var actValue = sync.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}