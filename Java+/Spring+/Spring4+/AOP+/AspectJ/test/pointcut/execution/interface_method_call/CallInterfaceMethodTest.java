package pointcut.execution.interface_method_call;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MyAspect.class, ConverterImpl.class})
public class CallInterfaceMethodTest {

    @Autowired
    private ConverterInterface reverse;

    @Test
    public void test() {
        assertThat(reverse.reverse("abc"), equalTo(MyAspect.PREFIX + "cba"));
    }
}
