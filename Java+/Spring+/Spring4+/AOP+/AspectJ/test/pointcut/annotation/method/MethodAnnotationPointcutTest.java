package pointcut.annotation.method;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {HelloMethodAspect.class, Messenger.class})
class MethodAnnotationPointcutTest {

    @Autowired
    private Messenger writer;

    @Test
    void test() {
        writer.writeMessage();
    }
}
