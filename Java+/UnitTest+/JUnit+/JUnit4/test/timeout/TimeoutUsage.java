package timeout;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Падение тестов по таймауту.
 */
public class TimeoutUsage {

    @Test(timeout = 200)
    public void timeout() throws Exception {
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
