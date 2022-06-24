import net.sf.cglib.reflect.FastClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Вызов метода с помощью FastMethod.
 */
class FastMethodTest {
    @Test
    void test() throws Exception {
        var fastClass = FastClass.create(SampleBean.class);
        var fastMethod = fastClass.getMethod(SampleBean.class.getMethod("getValue"));
        var bean = new SampleBean();
        bean.setValue("Hello cglib!");
        assertThat(fastMethod.invoke(bean, new Object[0])).isEqualTo("Hello cglib!");
    }
}
