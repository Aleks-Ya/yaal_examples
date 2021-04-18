package custom_runner.test_hierarchy.rule.tests;

import org.junit.jupiter.api.Test;
import custom_runner.test_hierarchy.rule.State;

import static org.junit.Assert.assertEquals;

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
