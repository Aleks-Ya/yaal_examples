package parallel.execution_mode;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import parallel.BaseTest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Execution(ExecutionMode.SAME_THREAD)
class SameThreadTest extends BaseTest {
    private static final List<String> threads = new CopyOnWriteArrayList<>();

    @Test
    public void test1() {
        printCurrentThread();
        threads.add(currentThreadName());
        wait3Seconds();
    }

    @Test
    public void test2() {
        printCurrentThread();
        threads.add(currentThreadName());
        wait3Seconds();
    }

    @Test
    public void test3() {
        printCurrentThread();
        threads.add(currentThreadName());
        wait3Seconds();
    }

    @AfterAll
    static void afterAll() {
        assertEquals(3, threads.size());
        threads.forEach(thread -> assertEquals(currentThreadName(), thread));
    }
}