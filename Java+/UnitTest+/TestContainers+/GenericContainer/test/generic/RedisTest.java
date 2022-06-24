package generic;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class RedisTest {

    @Container
    @SuppressWarnings("resource")
    private final GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

    @Test
    void setAndGet() {
        assertThat(redis.isRunning()).isTrue();
        try (var jedis = new Jedis(redis.getHost(), redis.getMappedPort(6379))) {
            var key = "key1".getBytes();
            var expValue = "value1".getBytes();
            jedis.set(key, expValue);
            var actValue = jedis.get(key);
            assertThat(actValue).isEqualTo(expValue);
        }
    }
}
