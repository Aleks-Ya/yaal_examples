import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class CloneTest {

    @Test
    void cloneBean() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        A origin = new A();
        origin.setB(7);
        A clone = (A) BeanUtils.cloneBean(origin);
        assertThat(origin).isNotSameAs(clone);
        assertThat(origin).isEqualTo(clone);
    }

    public static class A {
        private int b;

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            A a = (A) o;
            return b == a.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(b);
        }
    }
}