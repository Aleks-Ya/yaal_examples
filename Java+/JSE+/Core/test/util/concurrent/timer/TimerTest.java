package util.concurrent.timer;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.assertTrue;

public class TimerTest {
    private boolean run = false;

    @Test
    public void oneTimeExecution() throws InterruptedException {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                run = true;
            }
        };
        Timer timer = new Timer();
        int delay = 500;
        timer.schedule(task, delay);
        Thread.sleep(delay * 2);
        assertTrue(run);
    }
}
