package lang.string.string;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class StartsWith {
    @Test
    public void test() {
        assertTrue("abc".startsWith("abc"));
    }
}
