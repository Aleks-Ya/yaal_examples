package jmockit;

import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemMockTest {
    @Test
    @Disabled("JMockit didn't get initialized; please check the -javaagent JVM initialization parameter was used")
    public void testAddNowDate() {
        new SystemMock();
        long currentTime = System.currentTimeMillis();
        assertEquals(10000000L, currentTime);
    }

    private static class SystemMock extends MockUp<System> {
        @Mock
        public static long currentTimeMillis() {
            return 10000000L;
        }
    }
}
