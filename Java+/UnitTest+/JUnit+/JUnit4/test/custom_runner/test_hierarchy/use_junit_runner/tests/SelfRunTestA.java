package custom_runner.test_hierarchy.use_junit_runner.tests;

import custom_runner.test_hierarchy.use_junit_runner.State;
import custom_runner.test_hierarchy.use_junit_runner.TestHierarchyRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(TestHierarchyRunner.class)
public class SelfRunTestA {
    private State state;

    @Test
    public void main() {
        System.out.println("SelfRunTestA#main");
        assertEquals("Init state", state.message);
        state.message = "Right state after A";
//        fail();
    }

    @Test
    public void testOther() {
        System.out.println("SelfRunTestA#testOther");
        assertEquals("Init state", state.message);
        state.message = "Wrong state after A";
//        fail();
    }
}
