package pointcut.annotation.clazz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HelloClassAspect.class, Messenger.class})
public class ClassAnnotationPointcutTest {

    @Autowired
    private Messenger writer;

    @Test
    void test() {
        writer.writeMessage();
    }
}
