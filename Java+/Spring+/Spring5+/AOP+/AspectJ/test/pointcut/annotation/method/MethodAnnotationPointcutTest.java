package pointcut.annotation.method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HelloMethodAspect.class, Messenger.class})
public class MethodAnnotationPointcutTest {

    @Autowired
    private Messenger writer;

    @Test
    public void test() {
        writer.writeMessage();
    }
}
