package pointcut.execution;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConverterAspect.class, Converter.class})
public class ConverterTest {

    @Autowired
    private Converter writer;

    @Test
    public void test() {
        assertThat(writer.toUpperCase("a"), equalTo(ConverterAspect.PREFIX + "A"));
    }
}
