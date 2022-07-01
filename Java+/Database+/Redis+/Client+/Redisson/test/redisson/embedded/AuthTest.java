package redisson.embedded;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.client.RedisConnectionException;
import org.redisson.client.RedisException;
import org.redisson.config.Config;
import redis.embedded.RedisServer;

import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthTest {
    private static final int PORT = 6377;
    private static final String PASSWORD = "pass1";
    private RedisServer redisServer;

    @BeforeEach
    private void runServer() {
        redisServer = RedisServer.builder().port(PORT).setting("requirepass " + PASSWORD).build();
        redisServer.start();
    }

    @Test
    void authSuccess() {
        var config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:" + PORT).setPassword(PASSWORD);
        var redisson = Redisson.create(config);
        var success = redisson.getRedisNodes(RedisNodes.SINGLE).pingAll();
        assertThat(success).isTrue();
        redisson.shutdown();
    }

    @Test
    void authNoPassword() {
        assertThatThrownBy(() -> {
            var config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:" + PORT);
            var redisson = Redisson.create(config);
            redisson.getRedisNodes(RedisNodes.SINGLE).pingAll();
        })
                .isInstanceOf(RedisConnectionException.class).hasMessageContaining("Unable to connect to Redis server")
                .cause().isInstanceOf(CompletionException.class).hasMessageContaining("NOAUTH Authentication required.")
                .cause().isInstanceOf(RedisException.class).hasMessageContaining("NOAUTH Authentication required.");
    }

    @Test
    void authInvalidPassword() {
        assertThatThrownBy(() -> {
            var config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:" + PORT).setPassword("wrong password");
            var redisson = Redisson.create(config);
            redisson.getRedisNodes(RedisNodes.SINGLE).pingAll();
        })
                .isInstanceOf(RedisConnectionException.class).hasMessageContaining("Unable to connect to Redis server")
                .cause().isInstanceOf(CompletionException.class).hasMessageContaining("ERR invalid password.")
                .cause().isInstanceOf(RedisException.class).hasMessageContaining("ERR invalid password.");
    }

    @AfterEach
    private void stopServer() {
        redisServer.stop();
    }
}