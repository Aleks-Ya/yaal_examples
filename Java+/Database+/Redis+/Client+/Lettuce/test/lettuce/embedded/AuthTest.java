package lettuce.embedded;

import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.RedisConnectionException;
import lettuce.Factory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.embedded.RedisServer;

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
        try (var connection = Factory.newConnection(PORT, PASSWORD)) {
            var response = connection.sync().ping();
            assertThat(response).isEqualTo("PONG");
        }
    }

    @Test
    @SuppressWarnings("resource")
    void authNoPass() {
        assertThatThrownBy(() -> Factory.newConnection(PORT))
                .isInstanceOf(RedisConnectionException.class).hasMessageContaining("Unable to connect to ")
                .cause().isInstanceOf(RedisCommandExecutionException.class).hasMessageContaining("NOAUTH Authentication required.");
    }

    @Test
    @SuppressWarnings("resource")
    void authInvalidPass() {
        assertThatThrownBy(() -> Factory.newConnection(PORT, "wrong password"))
                .isInstanceOf(RedisConnectionException.class).hasMessageContaining("Unable to connect to ")
                .cause().isInstanceOf(RedisCommandExecutionException.class).hasMessageContaining("ERR invalid password");
    }

    @AfterEach
    private void stopServer() {
        redisServer.stop();
    }
}