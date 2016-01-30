package pointcut.execution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {
        ConverterAspect.class,
        Converter.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ConverterTest {

    @Autowired
    Converter writer;

    @Test
    public void test() {
        assertThat(writer.toUpperCase("a"), equalTo(ConverterAspect.PREFIX + "A"));
    }
}
