package collections;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InternerTest {

    @Test
    public void emptyToNull() {
        ImmutableObject obj1 = new ImmutableObject(1);
        ImmutableObject obj2 = new ImmutableObject(1);
        assertFalse(obj1 == obj2);

        Interner<ImmutableObject> interner = Interners.newStrongInterner();

        assertTrue(obj1 == interner.intern(obj1));
        assertTrue(obj1 == interner.intern(obj2));
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

            ImmutableObject that = (ImmutableObject) o;

            return number == that.number;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }
}