package util.concurrent.timer;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.assertj.core.api.Assertions.assertThat;

class TimerTest {
    private boolean run = false;

    @Test
    void oneTimeExecution() throws InterruptedException {
        var task = new TimerTask() {
            @Override
            public void run() {
                run = true;
            }
        };
        var timer = new Timer();
        var delay = 500;
        timer.schedule(task, delay);
        Thread.sleep(delay * 2);
        assertThat(run).isTrue();
    }
}
