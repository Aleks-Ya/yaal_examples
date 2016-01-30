package pointcut.execution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {
        HelloAspect.class,
        MessageWriter.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ExecutionPointcutTest {

    @Autowired
    MessageWriter writer;

    @Test
    public void test() {
        writer.writeMessage();
    }
}
