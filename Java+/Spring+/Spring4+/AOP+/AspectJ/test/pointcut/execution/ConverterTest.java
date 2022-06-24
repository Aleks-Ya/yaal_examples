package pointcut.execution;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ConverterAspect.class, Converter.class})
public class ConverterTest {

    @Autowired
    private Converter writer;

    @Test
    void test() {
        assertThat(writer.toUpperCase("a"), equalTo(ConverterAspect.PREFIX + "A"));
    }
}
