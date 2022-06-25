package lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prepare: run Redis Server (see "CLI/Redis/RedisServer.md").
 */
class LocalRedisServerTest {

    @Test
    void connect() {
        var client = RedisClient.create("redis://127.0.0.1:6379");
        try (var connection = client.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            var expValue = "value1";
            var key = "key1";
            sync.set(key, expValue);
            var actValue = sync.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}