import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Вызов метода с помощью FastMethod.
 */
public class FastMethodTest {
    @Test
    public void test() throws Exception {
        FastClass fastClass = FastClass.create(SampleBean.class);
        FastMethod fastMethod = fastClass.getMethod(SampleBean.class.getMethod("getValue"));
        SampleBean bean = new SampleBean();
        bean.setValue("Hello cglib!");
        assertEquals("Hello cglib!", fastMethod.invoke(bean, new Object[0]));
    }
}
