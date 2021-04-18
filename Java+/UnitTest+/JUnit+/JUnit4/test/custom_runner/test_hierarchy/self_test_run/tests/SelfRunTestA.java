package custom_runner.test_hierarchy.self_test_run.tests;

import custom_runner.test_hierarchy.self_test_run.State;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import custom_runner.test_hierarchy.self_test_run.MyRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(MyRunner.class)
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
