package run_one_test;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RunOneTestRunner.class)
public class RunOneTest {
    @Test
    public void testName() {
        System.out.println("My test method");
    }
}
