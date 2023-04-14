package micrometer;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalRegistryTest {
    @Test
    void global() {
        var globalRegistry = Metrics.globalRegistry;
        assertThat(globalRegistry).isInstanceOf(CompositeMeterRegistry.class);
        var simpleRegistry = new SimpleMeterRegistry();
        globalRegistry.add(simpleRegistry);
        var counter = Metrics.counter("errors");
        counter.increment();
        assertThat(counter.count()).isEqualTo(1);
    }
}
