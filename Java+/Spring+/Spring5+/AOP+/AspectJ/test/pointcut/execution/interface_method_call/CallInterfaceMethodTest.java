package pointcut.execution.interface_method_call;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MyAspect.class, ConverterImpl.class})
class CallInterfaceMethodTest {

    @Autowired
    private ConverterInterface reverse;

    @Test
    void test() {
        assertThat(reverse.reverse("abc")).isEqualTo(MyAspect.PREFIX + "cba");
    }
}
