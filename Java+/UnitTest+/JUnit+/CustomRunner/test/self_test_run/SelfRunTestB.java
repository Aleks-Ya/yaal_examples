package self_test_run;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(MyRunner.class)
@DependsOn(SelfRunTestA.class)
public class SelfRunTestB {

    private State state;

    @Test
    public void testInTestB() {
        System.out.println("SelfRunTestB speaks");
        assertNotNull(state);
        assertEquals("Right state after A", state.message);
        state.message = "State after B";
//        fail();
    }
}
