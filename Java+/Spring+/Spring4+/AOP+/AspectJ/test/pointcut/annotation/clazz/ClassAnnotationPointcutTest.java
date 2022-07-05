package pointcut.annotation.clazz;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {HelloClassAspect.class, Messenger.class})
class ClassAnnotationPointcutTest {

    @Autowired
    private Messenger writer;

    @Test
    void test() {
        writer.writeMessage();
    }
}
