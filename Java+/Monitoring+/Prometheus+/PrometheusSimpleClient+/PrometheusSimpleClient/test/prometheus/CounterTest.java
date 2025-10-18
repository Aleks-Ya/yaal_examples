package prometheus;

import io.prometheus.client.Counter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CounterTest {
    @Test
    void inc() {
        var counter = Counter.build().name("my_counter_1").help("My counter").register();
        assertThat(counter.get()).isEqualTo(0);
        counter.inc();
        assertThat(counter.get()).isEqualTo(1);
    }
}

