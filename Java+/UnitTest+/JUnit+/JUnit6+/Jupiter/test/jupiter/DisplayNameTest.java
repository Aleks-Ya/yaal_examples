package jupiter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("The test class")
class DisplayNameTest {
    @DisplayName("The test method")
    void outer() {
    }

    @Nested
    @DisplayName("The nested test class")
    class NestedTest {
        @Test
        @DisplayName("The nested test method")
        void nested() {
        }
    }
}
