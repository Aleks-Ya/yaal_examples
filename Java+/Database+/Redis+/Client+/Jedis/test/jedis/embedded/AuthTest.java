package jedis.embedded;

import jedis.Factory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.exceptions.JedisDataException;
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
        try (var pool = Factory.newPool(PORT, PASSWORD);
             var jedis = pool.getResource()) {
            var response = jedis.ping();
            assertThat(response).isEqualTo("PONG");
        }
    }

    @Test
    @SuppressWarnings("resource")
    void authNoPass() {
        assertThatThrownBy(() -> Factory.newPool(PORT, null).getResource().ping())
                .isInstanceOf(JedisDataException.class)
                .hasMessageContaining("NOAUTH Authentication required.");
    }

    @Test
    @SuppressWarnings("resource")
    void authInvalidPass() {
        assertThatThrownBy(() -> Factory.newPool(PORT, "wrong password").getResource().ping())
                .isInstanceOf(JedisDataException.class)
                .hasMessageContaining("ERR invalid password");
    }

    @AfterEach
    private void stopServer() {
        redisServer.stop();
    }
}