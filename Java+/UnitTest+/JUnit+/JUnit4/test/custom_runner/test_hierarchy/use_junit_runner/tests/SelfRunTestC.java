package custom_runner.test_hierarchy.use_junit_runner.tests;

import custom_runner.test_hierarchy.use_junit_runner.DependsOn;
import custom_runner.test_hierarchy.use_junit_runner.State;
import custom_runner.test_hierarchy.use_junit_runner.TestHierarchyRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(TestHierarchyRunner.class)
@DependsOn(SelfRunTestB.class)
public class SelfRunTestC {

    private State state;

    @Test
    public void testInTestC() {
        System.out.println("SelfRunTestC speaks");
        assertNotNull(state);
        assertEquals("Right state after B", state.message);
        state.message = "State after C";
    }
}
