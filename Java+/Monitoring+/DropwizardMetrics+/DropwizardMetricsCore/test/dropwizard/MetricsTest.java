package dropwizard;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class SimpleMeterRegistryTest {
    @Test
    void report() {
        var metrics = new MetricRegistry();
        var requests = metrics.meter("requests");

        requests.mark();
        requests.mark();

        try (var reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build()) {
            reporter.report();
        }
    }
}
