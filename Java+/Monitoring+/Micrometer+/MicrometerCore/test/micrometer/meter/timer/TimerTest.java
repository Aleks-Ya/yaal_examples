package micrometer.meter.timer;

import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class TimerTest {
    @Test
    void recordCallable() throws Exception {
        var registry = new SimpleMeterRegistry();
        var timer = Timer
                .builder("my.timer")
                .description("a description of what this timer does") // optional
                .tags("region", "test") // optional
                .register(registry);
        int time1 = 200;
        timer.recordCallable(() -> {
            Thread.sleep(time1);
            return null;
        });
        int time2 = 600;
        timer.recordCallable(() -> {
            Thread.sleep(time2);
            return null;
        });
        assertThat(timer.count()).isEqualTo(2);
        assertThat(timer.max(TimeUnit.MILLISECONDS)).isCloseTo(time2, Offset.offset(3D));
        assertThat(timer.totalTime(TimeUnit.MILLISECONDS)).isCloseTo(time1 + time2, Offset.offset(3D));
    }
}
