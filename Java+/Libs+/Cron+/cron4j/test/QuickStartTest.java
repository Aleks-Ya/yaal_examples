import it.sauronsoftware.cron4j.Scheduler;
import org.junit.Test;

/**
 * Source: http://www.sauronsoftware.it/projects/cron4j/manual.php
 */
public class QuickStartTest {

    @Test
    public void test() throws InterruptedException {
        Scheduler s = new Scheduler();
        // Schedule a once-a-minute task.
        s.schedule("* * * * *", () -> System.out.println("Another minute ticked away..."));
        s.start();
        Thread.sleep(1000L * 60L * 10L);
        s.stop();
    }
}
