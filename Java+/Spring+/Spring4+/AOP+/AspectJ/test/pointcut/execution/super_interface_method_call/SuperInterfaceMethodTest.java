package pointcut.execution.super_interface_method_call;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MyAspect.class, ConverterImpl.class})
public class SuperInterfaceMethodTest {

    @Autowired
    private ConverterSuperInterface reverse;

    @Test
    void test() {
        assertThat(reverse.reverse("abc"), equalTo(MyAspect.PREFIX + "cba"));
    }
}
