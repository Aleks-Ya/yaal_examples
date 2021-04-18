package custom_runner.test_hierarchy.self_test_run.tests;

import custom_runner.test_hierarchy.self_test_run.State;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import custom_runner.test_hierarchy.self_test_run.DependsOn;
import custom_runner.test_hierarchy.self_test_run.MyRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MyRunner.class)
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
