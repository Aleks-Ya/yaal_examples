package self_test_run;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(MyRunner.class)
public class SelfRunTestA {
    private State state;

    @Test
    public void testInTestA() {
        System.out.println("SelfRunTestA speaks");
        assertNotNull(state);
        assertEquals("Init state", state.message);
        state.message = "State after A";
//        fail();
    }
}
