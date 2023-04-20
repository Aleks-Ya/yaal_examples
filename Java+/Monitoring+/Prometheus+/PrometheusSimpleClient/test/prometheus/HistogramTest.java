package prometheus;

import io.prometheus.client.Histogram;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HistogramTest {
    @Test
    void startTimer() throws InterruptedException {
        var histogram = Histogram.build().name("my_histogram_startTimer").help("My histogram").register();
        var timer = histogram.startTimer();
        Thread.sleep(500);
        var duration = timer.observeDuration();
        assertThat(duration).isCloseTo(0.5, Offset.offset(0.01));
    }

    @Test
    void startTimerMany() throws InterruptedException {
        var histogram = Histogram.build().name("my_histogram_startTimerMany").help("My histogram").register();
        var repetitions = 5;
        var sleep = 500;
        for (var i = 0; i < repetitions; i++) {
            var timer = histogram.startTimer();
            Thread.sleep(sleep);
            var duration = timer.observeDuration();
            assertThat(duration).isCloseTo(0.5, Offset.offset(0.01));
        }
        var value = histogram.labels().get();
        assertThat(value.sum).isCloseTo((double) (repetitions * sleep) / 1000, Offset.offset(0.01));
    }

    @Test
    void time() {
        var histogram = Histogram.build().name("my_histogram_time").help("My histogram").register();
        var timer = histogram.startTimer();
        histogram.time(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        var duration = timer.observeDuration();
        assertThat(duration).isCloseTo(0.5, Offset.offset(0.01));
    }
}

