package doc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

public class RedisBackedCacheIntTest {

    @Rule
    public GenericContainer redis = new GenericContainer<>("redis:5.0.3-alpine")
            .withExposedPorts(6379);

    @BeforeEach
    public void setUp() {
        String address = redis.getContainerIpAddress();
        Integer port = redis.getFirstMappedPort();

        // Now we have an address and port for Redis, no matter where it is running
//        underTest = new RedisBackedCache(address, port);
    }

    @Test
    public void testSimplePutAndGet() {
//        underTest.put("test", "example");

//        String retrieved = underTest.get("test");
//        assertEquals("example", retrieved);
    }
}
