package micrometer;

import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompositeMeterRegistryTest {
    @Test
    void composite() {
        var compositeRegistry = new CompositeMeterRegistry();
        var simpleRegistry = new SimpleMeterRegistry();
        compositeRegistry.add(simpleRegistry);

        var peopleCounter = compositeRegistry.counter("people");
        var eventCounter = simpleRegistry.counter("events");

        peopleCounter.increment();
        peopleCounter.increment(4);
        assertThat(peopleCounter.count()).isEqualTo(5);

        eventCounter.increment();
        eventCounter.increment(2);
        assertThat(eventCounter.count()).isEqualTo(3);

        assertThat(compositeRegistry.getRegistries()).containsOnly(simpleRegistry);
    }
}
