package micrometer.prometheus;

import io.micrometer.common.lang.NonNull;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleMeterRegistryTest {
    @Test
    @Disabled("Not finished")
    void prometheus() {
        var config = new PrometheusConfig() {
            @Override
            public String get(@NonNull String key) {
                return null;
            }
        };
        var registry = new PrometheusMeterRegistry(config);
        var counter = registry.counter("events");
        counter.increment();
        counter.increment(2);
        var value = counter.count();
        assertThat(value).isEqualTo(3);
    }
}
