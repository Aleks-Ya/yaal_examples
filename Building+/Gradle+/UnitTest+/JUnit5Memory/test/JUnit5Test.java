import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JUnit5Test {
    @Test
    void test1() {
		var actMem = Runtime.getRuntime().maxMemory() / 1024 / 1024;
		var expMem = 512;
        assertEquals(expMem, actMem);
    }
}