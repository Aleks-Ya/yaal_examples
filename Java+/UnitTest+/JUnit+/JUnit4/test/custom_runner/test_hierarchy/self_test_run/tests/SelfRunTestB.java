package custom_runner.test_hierarchy.self_test_run.tests;

import custom_runner.test_hierarchy.self_test_run.State;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import custom_runner.test_hierarchy.self_test_run.DependsOn;
import custom_runner.test_hierarchy.self_test_run.MyRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(MyRunner.class)
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
