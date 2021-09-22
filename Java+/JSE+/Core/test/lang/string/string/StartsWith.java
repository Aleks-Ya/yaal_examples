package lang.string.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StartsWith {
    @Test
    void test() {
        assertTrue("abc".startsWith("abc"));
    }
}
