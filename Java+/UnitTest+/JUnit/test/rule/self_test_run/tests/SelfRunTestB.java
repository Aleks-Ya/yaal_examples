package rule.self_test_run.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import rule.self_test_run.DependsOn;
import rule.self_test_run.DependsOnRule;
import rule.self_test_run.MyRunner;
import rule.self_test_run.State;

import static org.junit.Assert.assertEquals;

@DependsOn(SelfRunTestA.class)
public class SelfRunTestB {

    @Rule
    public DependsOnRule dependsOnRule = new DependsOnRule();

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
