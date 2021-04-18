package custom_runner.notifier;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(PrepareDescriptionRunner.class)
public class NotifierTest {
    @Test
    public void testA() {
        System.out.println("NotifierTest A");
    }

    @Test
    public void testB() {
        System.out.println("Notifier B");
    }
}
