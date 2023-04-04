package execution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
class AspectTest {
    @Autowired
    private MessageCreator writer;

    @Test
    void execute() {
        assertThat(new MessageCreator().createMessage()).isEqualTo("World");
        assertThat(writer.createMessage()).isEqualTo("Hello, World!");
    }
}
