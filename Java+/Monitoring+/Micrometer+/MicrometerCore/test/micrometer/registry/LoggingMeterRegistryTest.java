package micrometer.registry;

import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;
import org.junit.jupiter.api.Test;
import util.InputStreamUtil;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class LoggingMeterRegistryTest {
    @Test
    void logging() {
        try (var stdErr = InputStreamUtil.redirectStdErr()) {
            var config = new LoggingRegistryConfig() {
                @Override
                public String get(@Nullable String key) {
                    return null;
                }

                @Override
                @NonNull
                public Duration step() {
                    return Duration.ofSeconds(1);
                }
            };
            var registry = new LoggingMeterRegistry(config, Clock.SYSTEM);
            var counter = registry.counter("events");
            counter.increment();
            counter.increment(2);
            await().untilAsserted(() -> assertThat(stdErr.toString()).contains("events{} throughput=3/s"));
            System.out.println(stdErr);
        }
    }
}
