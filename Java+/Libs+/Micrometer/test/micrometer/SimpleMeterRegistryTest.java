package micrometer;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleMeterRegistryTest {
    @Test
    void simple() {
        var registry = new SimpleMeterRegistry();
        var counter = registry.counter("events");
        counter.increment();
        counter.increment(2);
        var value = counter.count();
        assertThat(value).isEqualTo(3);
    }
}
