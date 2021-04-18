package custom_runner.notifier;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PrepareDescriptionRunner.class)
public class NotifierTest2 {
    @Test
    public void testA2() {
        System.out.println("NotifierTest A2");
    }

    @Test
    public void testB2() {
        System.out.println("Notifier B2");
    }
}
