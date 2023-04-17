package prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldPrometheusTest {
    private Counter helloWorldCounter;

    @BeforeEach
    void setUp() {
        var customRegistry = new CollectorRegistry();
        helloWorldCounter = Counter.build()
                .name("hello_world_total")
                .help("Total number of 'Hello, World!' messages displayed.")
                .register(customRegistry);
    }

    @Test
    void testHelloWorldCounter() {
        assertEquals(0, helloWorldCounter.get());
        helloWorldCounter.inc();
        assertEquals(1, helloWorldCounter.get());
    }
}

