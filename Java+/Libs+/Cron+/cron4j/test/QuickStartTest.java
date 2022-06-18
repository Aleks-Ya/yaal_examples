import it.sauronsoftware.cron4j.Scheduler;
import org.junit.jupiter.api.Test;

/**
 * Source: http://www.sauronsoftware.it/projects/cron4j/manual.php
 */
class QuickStartTest {

    @Test
    void test() throws InterruptedException {
        var s = new Scheduler();
        // Schedule a once-a-minute task.
        s.schedule("* * * * *", () -> System.out.println("Another minute ticked away..."));
        s.start();
        Thread.sleep(1000L * 60L * 10L);
        s.stop();
    }
}
