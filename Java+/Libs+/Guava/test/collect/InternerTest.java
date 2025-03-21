package collect;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InternerTest {

    @Test
    void emptyToNull() {
        var obj1 = new ImmutableObject(1);
        var obj2 = new ImmutableObject(1);
        assertThat(obj1 == obj2).isFalse();

        Interner<ImmutableObject> interner = Interners.newStrongInterner();

        assertThat(obj1 == interner.intern(obj1)).isTrue();
        assertThat(obj1 == interner.intern(obj2)).isTrue();
    }

    private static class ImmutableObject {
        private final int number;

        ImmutableObject(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            var that = (ImmutableObject) o;

            return number == that.number;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }
}