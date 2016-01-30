package pointcut.annotation.clazz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {
        HelloClassAspect.class,
        Messenger.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ClassAnnotationPointcutTest {

    @Autowired
    Messenger writer;

    @Test
    public void test() {
        writer.writeMessage();
    }
}
