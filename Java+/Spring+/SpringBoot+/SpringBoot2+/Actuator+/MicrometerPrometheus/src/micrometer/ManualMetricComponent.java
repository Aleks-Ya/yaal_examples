package micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ManualMetricComponent {
    @Autowired
    private MeterRegistry meterRegistry;

    void measure() {
        var counter = meterRegistry.counter("my_counter_1");
        counter.increment();
    }
}
