package pointcut.execution.interface_method_call;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MyAspect.class, ConverterImpl.class})
class CallInterfaceMethodTest {

    @Autowired
    private ConverterInterface reverse;

    @Test
    void test() {
        assertThat(reverse.reverse("abc")).isEqualTo(MyAspect.PREFIX + "cba");
    }
}
