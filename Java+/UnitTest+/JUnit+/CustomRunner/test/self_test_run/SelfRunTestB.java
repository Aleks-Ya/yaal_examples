package self_test_run;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(MyRunner.class)
@DependsOn(SelfRunTestA.class)
public class SelfRunTestB {
    @Test
    public void testInTestB() {
        System.out.println("SelfRunTestB speaks");
//        fail();
    }
}
