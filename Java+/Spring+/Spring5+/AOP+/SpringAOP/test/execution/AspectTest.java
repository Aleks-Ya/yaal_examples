package execution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Config.class})
@ExtendWith(SpringExtension.class)
class AspectTest {

    @Autowired
    MessageWriter writer;

    @Test
    void test() {
        writer.writeMessage();
    }
}
