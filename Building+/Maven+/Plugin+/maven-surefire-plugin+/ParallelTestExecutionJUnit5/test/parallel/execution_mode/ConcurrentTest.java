package parallel.execution_mode;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import parallel.BaseTest;

@Execution(ExecutionMode.CONCURRENT)
class ConcurrentTest extends BaseTest {

    @Test
    public void test1() {
        printCurrentThread();
        wait3Seconds();
    }

    @Test
    public void test2() {
        printCurrentThread();
        wait3Seconds();
    }
}