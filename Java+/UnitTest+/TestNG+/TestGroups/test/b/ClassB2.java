package b;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNull;

public class ClassB2 {
    @Test(groups = "b_not_run")
    public void testName() {
        System.out.println("ClassB2");
    }
}
