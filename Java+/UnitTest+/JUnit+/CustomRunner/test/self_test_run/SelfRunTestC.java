package self_test_run;

import org.junit.Test;
import org.junit.runner.RunWith;

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
        assertEquals("State after B", state.message);
        state.message = "State after C";
    }
}
