package jupiter;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NestedClassTest {
    @Test
    void outer() {
    }

    @Nested
    class NestedTest1 {
        @Test
        void nested1() {
        }
    }

    @Nested
    class NestedTest2 {
        @Test
        void nested2() {
        }
    }
}
