package custom_runner.test_hierarchy.rule.tests;

import org.junit.Test;
import custom_runner.test_hierarchy.rule.State;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
