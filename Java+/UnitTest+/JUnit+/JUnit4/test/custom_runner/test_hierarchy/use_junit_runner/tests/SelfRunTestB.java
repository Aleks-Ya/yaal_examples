package custom_runner.test_hierarchy.use_junit_runner.tests;

import custom_runner.test_hierarchy.use_junit_runner.DependsOn;
import custom_runner.test_hierarchy.use_junit_runner.State;
import custom_runner.test_hierarchy.use_junit_runner.TestHierarchyRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(TestHierarchyRunner.class)
@DependsOn(SelfRunTestA.class)
public class SelfRunTestB {

    private State state;

    @Test
    public void main() {
        System.out.println("SelfRunTestB speaks");
        assertEquals("Right state after A", state.message);
        state.message = "Right state after B";
//        fail();
    }


    @Test
    public void testInTestB() {
        System.out.println("SelfRunTestB speaks");
        assertEquals("Right state after A", state.message);
        state.message = "Wront state after B";
//        fail();
    }
}
