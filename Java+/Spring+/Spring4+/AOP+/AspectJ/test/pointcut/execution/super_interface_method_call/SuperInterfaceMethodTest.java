package pointcut.execution.super_interface_method_call;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = {
        MyAspect.class,
        ConverterImpl.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class SuperInterfaceMethodTest {

    @Autowired
    ConverterSuperInterface reverse;

    @Test
    public void test() {
        assertThat(reverse.reverse("abc"), equalTo(MyAspect.PREFIX + "cba"));
    }
}
