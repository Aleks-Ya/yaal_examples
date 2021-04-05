import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CloneTest {

    @Test
    public void main() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        A origin = new A();
        origin.setB(7);
        A clone = (A) BeanUtils.cloneBean(origin);
        assertFalse(origin == clone);
        assertEquals(origin, clone);
    }

    public static class A {
        private int b;

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }
}