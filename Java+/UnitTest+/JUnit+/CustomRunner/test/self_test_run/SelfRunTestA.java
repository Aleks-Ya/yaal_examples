package self_test_run;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(MyRunner.class)
public class SelfRunTestA {

    @Test
    public void testInTestA() {
        System.out.println("SelfRunTestA speaks");
//        fail();
    }
}
