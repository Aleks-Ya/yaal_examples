package rule.self_test_run.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import rule.self_test_run.DependsOn;
import rule.self_test_run.MyRunner;
import rule.self_test_run.State;

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
