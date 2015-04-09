package b;

import org.testng.annotations.Test;

public class ClassB1 {
    @Test(groups = "b_to_run")
    public void testName() {
        System.out.println("ClassB1");
    }
}
