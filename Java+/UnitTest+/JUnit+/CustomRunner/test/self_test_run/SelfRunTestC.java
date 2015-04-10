package self_test_run;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MyRunner.class)
@DependsOn(SelfRunTestB.class)
public class SelfRunTestC {
    @Test
    public void testInTestC() {
        System.out.println("SelfRunTestC speaks");
    }
}
