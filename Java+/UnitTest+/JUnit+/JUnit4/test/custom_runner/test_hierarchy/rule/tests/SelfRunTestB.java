package custom_runner.test_hierarchy.rule.tests;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import custom_runner.test_hierarchy.rule.DependsOn;
import custom_runner.test_hierarchy.rule.DependsOnRule;
import custom_runner.test_hierarchy.rule.State;

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
